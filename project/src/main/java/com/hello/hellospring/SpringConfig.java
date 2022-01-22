package com.hello.hellospring;

import com.hello.hellospring.aop.TimeTraceAop;
import com.hello.hellospring.repository.*;
import com.hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {


    //  private DataSource dataSource;
/*
    private EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em) {
      this.em = em;
  }
*/
    private final MemberRepository memberRepository;

    @Autowired // 생성자가 하나일 경우는 @Autowired 생략 가능
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 이런 경우에는 스프링 컨테이너가 멤버리포지토리를 찾고,
    // 등록한게 없는데? 인터페이스만 만들었는데 jpaRepository 해놓으면
    // 스프링데이터 제피에이가 인터페이스에대한 프록시 기술로 구현체를 만들어내놓고.
    // 그리고 스프링 빈에 등록을 해놓음
    // 그래서 인젝션 받을 수 있다.
    //

    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//          return new JpaMemberRepository(em);

//    }
}
