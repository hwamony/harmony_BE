package com.example.harmony.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleEnum {

    FATHER("아빠"),
    MOTHER("엄마"),
    FIRST("첫째"),
    SECOND("둘째"),
    NTH("N째"),
    YOUNGEST("막내"),
    ROOMMATE("동거인"),
    NOBODY("미설정");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static RoleEnum nameOf(String role) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if(roleEnum.getRole().equals(role)) {
                return roleEnum;
            }
        }
        return null;
    }

}
