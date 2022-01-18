package com.hello.hellospring.service;

import com.hello.hellospring.controller.HelloController;
import com.hello.hellospring.domain.Member;
import com.hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    public void beforeEach(){
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clearStore();
    }

    @Test
    void join() throws Exception{
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long SaveId = memberService.join(member);

        //then

        // the refactoring code not lecture code
        memoryMemberRepository.findById(SaveId)
                .ifPresent(m -> assertEquals(member.getName(),m.getName()));

    }

    @Test
    public void duplicateMemberTest() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("jung");
        Member member2 = new Member();
        member2.setName("jung");

        //when
        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }
}