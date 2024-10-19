# JPA

## 1.3 JPA란 무엇인가?
- 자바 진영의 ORM 기술 표준
- 애플리케이션과 JDBC 사이에서 동작
- EJB 3.0에서 하이버네이트를 기반으로 새로운 자바 ORM 기술 표준이 만들어졌는데 이것이 바로 JPA
- 자바 ORM 기술에 대한 API 표준 명세 -> JPA를 사용하려면 JPA를 구현한 ORM 프레임워크를 선택해야 하는데, 하이버네이트가 가장 대중적

## 2.6 애플리케이션 개발
- JPA를 사용하면 항상 트랜잭션 안에서 데이터를 변경해야 함. 트랜잭션 없이 데이터를 변경하면 예외 발생.
- 트랜잭션을 시작하려면 엔티티 매니저에서 트랜잭션 API를 받아와야 함.

## 3.3 엔티티의 생명주기
- 비영속(new/transient): 영속성 컨텍스트와 전혀 관계가 없는 상태. "순수한 객체"
- 영속(managed): 영속성 컨텍스트에 저장된 상태 -> 영속성 컨텍스트에 의해 관리된다는 뜻!(em.find()나 JPQL을 사용해서 조회한 엔티티도!)
- 준영속(detached): 영속성 컨텍스트에 저장되었다가 분리된 상태(em.close() 하거나 em.clear()해서 영속성 컨텍스트를 초기화해도 영속성 컨텍스트가 관리하던 영속 상태의 엔티티는 준영속 상태가 됨!)
- 삭제(removed): 삭제된 상태(영속성 컨텍스트, 데이터베이스 모두에서)

## 3.4 영속성 컨텍스트의 특징
- 영속 상태는 식별자 값이 반드시 있어야 함! -> 없으면 예외
- JPA는 보통 트랜잭션을 커밋하는 순간 영속성 컨텍스트에 새로 저장된 엔티티를 데이터베이스에 반영!(flush)
- 영속성 컨텍스트는 내부에 캐시를 가지고 있는데, 이것이 1차 캐시. 영속 상태의 엔티티는 모두 이곳에 저장됨.
  em.find()를 호출하면 먼저 1차 캐시에서 엔티티를 찾고 만약 찾는 엔티티가 없으면 데이터베이스에서 조회
- 쓰기 지연: 엔티티 매니저는 트랜잭션을 커밋하기 직전까지 데이터베이스에 엔티티를 저장하지 않고 내부 쿼리 저장소에 INSERT SQL을 모았다가 트랜잭션을 커밋할 때 모아둔 쿼리를 데이터베이스에 보냄
- 트랜잭션을 커밋하면 엔티티 매니저는 우선 영속성 컨텍스트를 플러시(영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화=쓰기 지연 SQL 저장소에 모인 쿼리를 데이터베이스에 보냄)
- JPA는 엔티티를 영속성 컨텍스트에 보관할 때, 최초 상태를 복사해서 저장(스냅샷) -> 플러시 시점에 스냅샷과 엔티티 비교해서 변경된 엔티티를 찾아 수정 쿼리 생성해서 쓰기 지연 SQL 저장소에 보냄
- 영속성 컨텍스트를 flush 하는 3가지 방법: 직접 호출, 트랜잭션 커밋 시 플러시 자동 호출, JPQL 쿼리 실행 시 플러시 자동 호출

## 4.1 @Entity
- @Entity 적용 시 주의사항
  - 기본 생성자는 필수(파라미터가 없는 public 또는 protected 생성자)
  - final 클래스, enum, interface, inner 클래스에는 사용할 수 없음
  - 저장할 필드에 final을 사용하면 안됨

## 4.3 다양한 매핑 사용
- 자바의 날짜 타입은 @Temporal을 사용해서 매핑

## 4.4 데이터베이스 스키마 자동 생성
- hibernate.hbm2ddl.auto 속성
  - create: drop + create
  - create-drop: drop + create + drop(애플리케이션 종료시 생성한 DDL 제거)
  - update: 변경사항만
  - validate: 차이가 있으면 경고를 남기고 애플리케이션 실행 X
  - none: 자동 생성 기능 사용 X

## 4.6 기본 키 매핑
- IDENTITY 전략은 데이터를 데이터베이스에 INSERT한 후에 기본 키 값을 조회할 수 있음. 따라서 엔티티에 식별자 값을 할당하려면 JPA는 추가로 데이터베이스를 조회해야 함. JDBC3에서 추가된 Statement.getGeneratedKeys()를 사용하면 데이터를 저장하면서 동시에 생성된 기본 키 값도 얻어올 수 있음.
- IDENTITY 식별자 생성 전략은 엔티티를 데이터베이스에 저장해야 식별자를 구할 수 있으므로 em.persist()를 호출하는 즉시 INSERT SQL이 데이터베이스에 전달됨 -> 이 전략은 트랜잭션을 지원하는 쓰기 지연이 동작 X

## 5.4 연관관계의 주인
- 연관관계의 주인만이 데이터베이스 연관관계와 매핑되고 외래 키를 관리(등록, 수정, 삭제)할 수 있음! 주인이 아닌 쪽은 읽기만 할 수 있음
  - 주인은 mappedBy 속성을 사용하지 않음
- 연관관계의 주인은 테이블에 외래 키가 있는 곳으로 정해야 함!
- 다대일, 일대다 관계에서는 항상 다 쪽이 외래 키를 가짐! -> @ManyToOne은 항상 연관관계의 주인이 되므로 mappedBy 속성이 없음

## 5.6 양방향 연관관계의 주의점
- 객체 관점에서 양쪽 방향에 모두 값을 입력해주는 것이 가장 안전!

## 6.2 일대다
- 일대다 단방향 관계를 매핑할 때는 @JoinColumn을 명시해야 함! 그렇지 않으면 JPA는 연결 테이블을 중간에 두고 연관관계를 관리하는 조인 테이블 전략을 기본으로 사용해서 매핑!
- 일대다 양방향 매핑이 완전히 불가능한 것은 아님. 일대다 단방향 매핑 반대편에 같은 외래 키를 사용하는 다대일 단방향 매핑을 읽기 전용으로 하나 추가하면 됨.

## 6.3. 일대일
- 일대일 관계는 주 테이블이나 대상 테이블 중에 누가 외래 키를 가질지 선택해야 함
- 주 테이블에 외래 키: 주 테이블이 외래 키를 가지고 있으므로 주 테이블만 확인해도 대상 테이블과 연관관계가 있는지 알 수 있음
- 대상 테이블에 외래 키: 테이블 관계를 일대일에서 일대다로 변경할 때 테이블 구조를 그대로 유지할 수 있음
- 일대일 관계 중 대상 테이블에 외래 키가 있는 단방향 관계는 JPA에서 지원하지 않음. 그리고 이런 모양으로 매핑할 수 있는 방법도 없음. 이때는 단방향 관계를 Locker에서 Member 방향으로 수정하거나, 양방향 관계로 만들고 Locker를 연관과계의 주인으로 설정해야 함.