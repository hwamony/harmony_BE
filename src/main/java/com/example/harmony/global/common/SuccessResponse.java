package com.example.harmony.global.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessResponse<T> extends Response {

    private T data;

    public SuccessResponse(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }

    public SuccessResponse(HttpStatus httpStatus, String msg, T data) {
        super(httpStatus, msg);
        this.data = data;
    }
}
