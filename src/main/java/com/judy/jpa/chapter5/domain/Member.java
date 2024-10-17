package com.judy.jpa.chapter5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
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

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTeam(Team team) {
        if (this.team != null) {
            this.team.getMembers().remove(this); // 다른 팀에서 참조하고 있는 것 삭제하고 세팅해야함!
        }
        this.team = team;
        team.getMembers().add(this);
    }
}
