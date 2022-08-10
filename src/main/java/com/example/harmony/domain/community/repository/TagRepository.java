package com.example.harmony.domain.community.repository;

import com.example.harmony.domain.community.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}