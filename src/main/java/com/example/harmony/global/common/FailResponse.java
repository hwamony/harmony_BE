package com.example.harmony.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FailResponse {

    private int code;

    private String msg;

    private List<String> errors;

    public FailResponse(HttpStatus httpStatus, String msg) {
        this.code = httpStatus.value();
        this.msg = msg;
    }

    public FailResponse(HttpStatus httpStatus, String msg, List<String> errors) {
        this.code = httpStatus.value();
        this.msg = msg;
        this.errors = errors;
    }
}
