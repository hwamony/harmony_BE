package com.example.harmony.global.common;

import org.springframework.http.HttpStatus;

import java.util.List;

public class FailResponse<T> extends Response {

    private List<String> errors;

    public FailResponse(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }

    public FailResponse(HttpStatus httpStatus, String msg, List<String> errors) {
        super(httpStatus, msg);
        this.errors = errors;
    }
}
