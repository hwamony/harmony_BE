package com.example.harmony.domain.community.repository;

import com.example.harmony.domain.community.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}