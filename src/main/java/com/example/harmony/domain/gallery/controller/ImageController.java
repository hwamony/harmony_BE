package com.example.harmony.domain.gallery.controller;

import com.example.harmony.domain.gallery.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;
}
