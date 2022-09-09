package com.example.harmony.domain.user.dto;

import com.example.harmony.domain.user.model.Family;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FamilyInfoResponse {

    private String familyName;
    private List<MembersResponse> members;
    private int level;
    private boolean flower;
    private int score;
    private int weeklyScore;

    public FamilyInfoResponse(Family family, List<MembersResponse> members) {
        this.familyName = family.getFamilyName();
        this.members = members;
        this.level = setLevel(family.getTotalScore());
        this.flower = family.isFlower();
        this.score = family.getTotalScore();
        this.weeklyScore = family.getWeeklyScore();
    }

    public int setLevel(int score) {
        int level;
        if (score > 2999) {
            level = 4;
        } else if (score > 769) {
            level = 3;
        } else if (score > 219) {
            level = 2;
        } else if (score > 54) {
            level = 1;
        } else {
            level = 0;
        }

        return level;
    }

}
