package com.example.harmony.domain.ranking.controller;

import com.example.harmony.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RankingController {

    private final RankingService rankingService;
}
