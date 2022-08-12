package com.example.harmony.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    FATHER("아빠"),
    MOTHER("엄마"),
    FIRST("첫째"),
    SECOND("둘째"),
    NTH("N째"),
    YOUNGEST("막내"),
    ROOMMATE("동거인");

    private final String role;

}
