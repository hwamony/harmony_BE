package com.example.harmony.domain.schedule.service;

import com.example.harmony.domain.schedule.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
}
