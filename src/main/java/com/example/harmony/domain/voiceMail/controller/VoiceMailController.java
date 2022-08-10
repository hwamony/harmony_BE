package com.example.harmony.domain.voiceMail.controller;

import com.example.harmony.domain.voiceMail.service.VoiceMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class VoiceMailController {

    private final VoiceMailService voiceMailService;
}
