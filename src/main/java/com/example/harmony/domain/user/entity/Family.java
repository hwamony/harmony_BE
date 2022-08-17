package com.example.harmony.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String familyName;

    private String familyCode;

    private int score;

    private int monthlyScore;

    private int totalScore;

    private boolean flower;

    @OneToMany(mappedBy = "family", fetch = FetchType.EAGER)
    private List<User> members;

    public Family(String familyName, String familyCode) {
        this.familyName = familyName;
        this.familyCode = familyCode;
    }

    public void plusScore(int score) {
        this.score += score;
    }

    public void minusScore(int score) {
        this.score -= score;
    }
}
