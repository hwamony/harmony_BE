package com.example.harmony.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Family family;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    // 카카오 로그인 구현시 닉네임 추가입력 필요
    @Column(unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String gender;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;

    public User(String email, String name, String nickname, String password, String gender) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.gender = gender;
    }
}