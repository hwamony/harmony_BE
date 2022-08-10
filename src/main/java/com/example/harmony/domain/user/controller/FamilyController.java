package com.example.harmony.domain.user.controller;

import com.example.harmony.domain.user.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FamilyController {

    private final FamilyService familyService;
}
