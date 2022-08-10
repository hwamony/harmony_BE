package com.example.harmony.domain.gallery.controller;

import com.example.harmony.domain.gallery.service.GalleryCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GalleryCommentController {

    private final GalleryCommentService galleryCommentService;
}
