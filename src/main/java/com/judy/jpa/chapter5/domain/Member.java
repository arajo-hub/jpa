package com.judy.jpa.chapter5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team; // Member - Team 사이에서 Member.team이 연관관계의 주인 -> 외래 키 관리를 여기에서 담당!

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
