package com.example.harmony.domain.community.repository;

import com.example.harmony.domain.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}