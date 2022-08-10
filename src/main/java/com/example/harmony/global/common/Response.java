package com.example.harmony.global.common;

import org.springframework.http.HttpStatus;

public class Response {

    private int code;

    private String msg;

    public Response(HttpStatus httpStatus, String msg) {
        this.code = httpStatus.value();
        this.msg = msg;
    }
}
