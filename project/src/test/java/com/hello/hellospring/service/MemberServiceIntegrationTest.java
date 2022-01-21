package com.hello.hellospring.service;

import com.hello.hellospring.domain.Member;
import com.hello.hellospring.repository.MemberRepository;
import com.hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
/**
 * DB는 Transaction 개념
 * Commit을 해야 db에 들어감
 * AutoCommit이냐 아니냐
 * Test 끝난담에 롤백해버리면 DB 데이터 다 날림
 * 그 롤백해주는 어노테이션이 @Transactional이 해줌
 * 즉 test 끝나면 DB에 반영이 안되고 Rollback 시켜줌.
  */
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void join(){
        //given
        Member member = new Member();
        member.setName("jung");

        //when
        Long SaveId = memberService.join(member);

        //then

        // the refactoring code not lecture code
        memberRepository.findById(SaveId)
                .ifPresent(m -> assertEquals(member.getName(),m.getName()));

    }

    @Test
    public void duplicateMemberTest(){
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