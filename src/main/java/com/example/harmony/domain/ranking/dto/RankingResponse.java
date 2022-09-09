package com.example.harmony.domain.ranking.dto;

import com.example.harmony.domain.user.model.Family;
import lombok.Getter;

import java.util.List;

@Getter
public class RankingResponse {

    private int level;
    private Boolean flower;
    private int weeklyScore;
    private int ranking;
    private List<Family> top10List;


    public RankingResponse(Family family, int ranking, int level, List top10List) {
        this.level = level;
        this.flower = family.isFlower();
        this.weeklyScore = family.getWeeklyScore();
        this.ranking = ranking;
        this.top10List = top10List;
    }


}
