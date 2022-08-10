package com.example.harmony.domain.gallery.repository;

import com.example.harmony.domain.gallery.entity.GalleryComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryCommentRepository extends JpaRepository<GalleryComment, Long> {
}