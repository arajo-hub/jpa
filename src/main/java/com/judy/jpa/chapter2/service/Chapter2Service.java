package com.judy.jpa.chapter2.service;

import com.judy.jpa.chapter2.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Chapter2Service {

    private final EntityManager em;

    @Transactional
    public void logic() {
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        // 등록
        em.persist(member);
        // insert into member (age,name,id) values (?,?,?)

        // 수정
        member.setAge(20);
        // update member set age=?,name=? where id=?
        // JPA는 어떤 엔티티가 변경되었는지 추적하는 기능을 가지고 있기 때문에
        // 엔티티의 값만 변경하면 UPDATE SQL을 생성해서 데이터베이스에 값을 변경한다!!!
        // 따로 저장하는 과정을 거치지 않아도 된다!!!

        // 한 건 조회
        Member findMember = em.find(Member.class, id);
        // select m1_0.id,m1_0.age,m1_0.name from member m1_0 where m1_0.id=?
        log.info("findMember={}, age={}", findMember.getUsername(), findMember.getAge());

        // 목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        // select m1_0.id,m1_0.age,m1_0.name from member m1_0
        log.info("members.size={}", members.size());

        em.remove(member);
        // delete from member where id=?
    }

}
