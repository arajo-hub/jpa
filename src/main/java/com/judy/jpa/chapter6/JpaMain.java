package com.judy.jpa.chapter6;

import com.judy.jpa.chapter6.domain.Product;
import com.judy.jpa.chapter6.domain.Team;
import com.judy.jpa.chapter6.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
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
            findInverse(em);
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

    public static void save(EntityManager em) {
        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품A");
        em.persist(productA);

        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("회원1");
        member1.getProducts().add(productA); // 연관관계 설정
        em.persist(member1);
    }

    public static void find(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        List<Product> products = member.getProducts(); // 객체 그래프 탐색
        for (Product product : products) {
            System.out.println("product.name = " + product.getName());
        }
    }

    public static void findInverse(EntityManager em) {
        Product product = em.find(Product.class, "productA");
        List<Member> members = product.getMembers();
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
        }
    }
}
