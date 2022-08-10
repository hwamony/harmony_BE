package com.example.harmony.domain.voiceMail.repository;

import com.example.harmony.domain.voiceMail.entity.VoiceMail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceMailRepository extends JpaRepository<VoiceMail, Long> {
}