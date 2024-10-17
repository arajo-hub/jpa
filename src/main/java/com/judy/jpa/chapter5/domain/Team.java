package com.judy.jpa.chapter5.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Team {

    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;

    @OneToMany(mappedBy = "team") // mappedBy는 양방향 매핑일 때 사용, 반대쪽 매핑의 필드 이름을 값으로 줌. member.team의 team.
    private List<Member> members = new ArrayList<Member>();

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
