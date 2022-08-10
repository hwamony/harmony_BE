package com.example.harmony.domain.schedule.controller;

import com.example.harmony.domain.schedule.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ParticipationController {

    private final ParticipationService participationService;
}
