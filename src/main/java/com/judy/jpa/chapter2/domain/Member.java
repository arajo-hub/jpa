package com.judy.jpa.chapter2.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity // 테이블과 매핑되는 클래스임을 알려줌
@Table(name = "MEMBER") // 매핑할 테이블 정보를 알려줌. 없으면 클래스 이름을 테이블 이름으로 매핑
@Getter
@Setter
public class Member {

    @Id // 식별자 필드임을 표시
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    // ↓ 매핑 정보 없는 필드 -> 필드명을 사용해서 컬럼명으로 매핑
    private Integer age;

}
