package com.example.harmony.domain.user.repository;

import com.example.harmony.domain.user.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Long> {
}