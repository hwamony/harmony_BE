package com.example.harmony.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Family {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String familyName;

    private String familyCode;

    private int monthlyScore;

    private int totalScore;

    private boolean flower;

    @OneToMany(mappedBy = "family")
    private List<User> members;
}
