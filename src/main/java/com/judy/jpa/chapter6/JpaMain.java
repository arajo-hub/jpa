package com.judy.jpa.chapter6;

import com.judy.jpa.chapter6.domain.Team;
import com.judy.jpa.chapter6.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JpaMain {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JpaMain.class, args);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            testSave(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
//        emf.close();
    }

    public static void testSave(EntityManager em) {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        Team team1 = new Team("team1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(member1); // INSERT(member1)
        em.persist(member2); // INSERT(member2)
        em.persist(team1); // INSERT(team1), UPDATE(member1, member2)
        // member1, member2를 persist하지 않고 team1만 persist하려고 하면
        // team1만 DB에 저장됨
    }
}
