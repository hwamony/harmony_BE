package com.example.harmony.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckResponse {

    private boolean exist;

    public CheckResponse(boolean exist) {
        this.exist = exist;
    }
}
