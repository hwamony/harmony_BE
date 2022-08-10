package com.example.harmony.domain.voiceMail.service;

import com.example.harmony.domain.voiceMail.repository.VoiceMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VoiceMailService {

    private final VoiceMailRepository voiceMailRepository;
}
