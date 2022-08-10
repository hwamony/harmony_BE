package com.example.harmony.domain.schedule.repository;

import com.example.harmony.domain.schedule.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}