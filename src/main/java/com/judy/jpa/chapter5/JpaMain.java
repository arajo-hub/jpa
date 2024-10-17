package com.judy.jpa.chapter5;

import com.judy.jpa.chapter5.domain.Member;
import com.judy.jpa.chapter5.domain.Team;
import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class JpaMain {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JpaMain.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            biDirection(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    public static void testSave(EntityManager em) {
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        team1.getMembers().add(member1); // 연관관계의 주인이 team.member가 아니므로 외래 키에 영향 X
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        team1.getMembers().add(member2); // 연관관계의 주인이 team.member가 아니므로 외래 키에 영향 X
        em.persist(member2);
    }

    public static void queryLogicJoin(EntityManager em) {
        String jpql = "select m from Member m join m.team t where " +
                "t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username=" +
                    member.getUsername());
        }
    }

    private static void updateRelation(EntityManager em) {
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);
    }

    private static void deleteRelation(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null); // 연관관계 제거
        System.out.println(member1.getTeam());
    }

    private static void biDirection(EntityManager em) {
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");
        team1.getMembers().add(member1);

        Member member2 = new Member("member2", "회원2");
        team1.getMembers().add(member2);

        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers(); // 객체 그래프 탐색

        for (Member member : members) {
            System.out.println("member.username = " +
                    member.getUsername());
        }
    }
}
