package com.example.harmony.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String name;

    // TODO: enum? String?
    private String role;

    // TODO: enum? String?
    private String gender;

    @ManyToOne
    private Family family;
}
