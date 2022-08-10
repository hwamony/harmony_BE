package com.example.harmony.domain.community.entity;

import com.example.harmony.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean like;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
