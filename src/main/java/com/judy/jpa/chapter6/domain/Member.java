package com.judy.jpa.chapter6.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    @OneToOne(mappedBy = "member")
    private Locker locker;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT", joinColumns = @JoinColumn(name = "MEMBER_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    // @ManyToMany와 @JoinTable을 이용해서 연결 테이블을 바로 매핑 -> Member_Product 엔티티 없이 매핑 가능!
    private List<Product> products = new ArrayList<Product>();

    public Member(String username) {
        this.username = username;
    }
}
