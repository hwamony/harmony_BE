package com.example.harmony.global.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessResponse<T> {

    private int code;

    private String msg;

    private T data;

    public SuccessResponse(HttpStatus httpStatus, String msg) {
        this.code = httpStatus.value();
        this.msg = msg;
    }

    public SuccessResponse(HttpStatus httpStatus, String msg, T data) {
        this.code = httpStatus.value();
        this.msg = msg;
        this.data = data;
    }
}
