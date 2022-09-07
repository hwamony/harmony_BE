package com.example.harmony.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

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

    private int monthlyScore;

    private boolean flower;

    @OneToMany(mappedBy = "family", fetch = FetchType.EAGER)
    private List<User> members;

    public Family(String familyName, String familyCode) {
        this.familyName = familyName;
        this.familyCode = familyCode;
    }

    public void plusScore(int score) {
        this.totalScore += score;
        this.monthlyScore += score;
    }

    public void minusScore(int score) {
        this.totalScore -= score;
        this.monthlyScore -= score;
    }

    @Scheduled(cron ="* * 5 1 * *")
    public void setFlower(){
        flower= true;
        plusScore(50);
    }
}
