package com.hello.hellospring.repository;

import com.hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // pk의 경우
        return Optional.ofNullable(em.find(Member.class,id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        /**
         * Entity 조회를 해 select의 대상은 member entity 자체를 조회.
         */
        return em.createQuery("select m from Member m where m.name=:name",Member.class)
                .setParameter("name",name)
                .getResultList().stream().findAny();

    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
}
