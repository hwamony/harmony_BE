package com.example.harmony.domain.community.repository;

import com.example.harmony.domain.community.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}