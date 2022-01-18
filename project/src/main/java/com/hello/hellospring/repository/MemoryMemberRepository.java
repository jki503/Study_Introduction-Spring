package com.hello.hellospring.repository;

import com.hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository{

    // 실무에서는 동시성 문제때문에 concurrencyMap을 써야함
    private static final Map<Long,Member> store = new HashMap<>();
    // 동시성 문제로 실무에서는 어텀롱을 써야한다.
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Null이 반환될 가능 있으면 optional 처리 하는 방식
        // client에서 뭘 할 수 있다..?
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();

    }

    @Override
    public List<Member> findAll() {
        // 자바에서 실무로 리스트를 많이 씀!
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
