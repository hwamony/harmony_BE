package com.example.harmony.domain.user.model;

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

    private int totalScore;

    private int weeklyScore;

    private boolean flower;

    @OneToMany(mappedBy = "family", fetch = FetchType.EAGER)
    private List<User> members;

    public Family(String familyName, String familyCode) {
        this.familyName = familyName;
        this.familyCode = familyCode;
    }

    public void plusScore(int score) {
        this.totalScore += score;
        this.weeklyScore += score;
    }

    public void minusScore(int score) {
        this.totalScore -= score;
        this.weeklyScore -= score;
    }

    public int getLevel() {
        if (totalScore < 55) {
            return 0;
        } else if (totalScore < 220) {
            return 1;
        } else if (totalScore < 770) {
            return 2;
        } else if (totalScore < 3000) {
            return 3;
        } else {
            return 4;
        }
    }

    public void setFlower(boolean flower) {
        this.flower = flower;
    }

    public void initWeeklyScore() {
        this.weeklyScore = 0;
    }
}
