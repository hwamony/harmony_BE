package com.example.harmony.global.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UploadResponse {

    private String url;

    private String filename;
}
