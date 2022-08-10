package com.example.harmony.domain.user.service;

import com.example.harmony.domain.user.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FamilyService {

    private final FamilyRepository familyRepository;
}
