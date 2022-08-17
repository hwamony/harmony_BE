package com.example.harmony.domain.user.dto;

import lombok.*;

@Setter
@Getter
public class CheckResponse {

    private boolean enable;

    public CheckResponse(boolean exist) {
        this.enable = exist;
    }
}
