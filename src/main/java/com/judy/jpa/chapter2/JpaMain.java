package com.judy.jpa.chapter2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMain {

    public static void main(String[] args) {
        // ↓ 엔티티 매니저 팩토리: 이름이 jpabook인 영속성 유닛을 찾아 엔티티 매니저 팩토리 생성
        // JPA를 동작시키기 위한 기반 객체를 만들고 JPA 구현체에 따라서는 데이터베이스 커넥션 풀도 생성하므로 엔티티 매니저 팩토리를 생성하는 비용은 아주 큼.
        // 따라서 딱 한 번만 생성하고 공유해서 사용해야 함!!!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        // ↓ 엔티티 매니저: JPA의 기능 대부분은 엔티티 매니저가 제공
        // 엔티티 매니저는 내부에 데이터소스를 유지하면서 데이터베이스와 통신
        // 스레드 간에 공유 or 재사용 금지!!!
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {
        log.info("logic 실행");
    }

}
