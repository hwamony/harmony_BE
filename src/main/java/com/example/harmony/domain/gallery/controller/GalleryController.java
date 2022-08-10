package com.example.harmony.domain.gallery.controller;

import com.example.harmony.domain.gallery.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GalleryController {

    private final GalleryService galleryService;
}
