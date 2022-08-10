package com.example.harmony.domain.gallery.repository;

import com.example.harmony.domain.gallery.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}