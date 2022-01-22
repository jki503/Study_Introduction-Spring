package com.hello.hellospring.repository;

import com.hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>, MemberRepository{

    //JPQL 생성 select m from Member m where m.name =?
    //JPQL 규칙...
    @Override
    Optional<Member> findByName(String name); // 비즈니스가 다를 경우 공통 메서드로는 한계..
}
