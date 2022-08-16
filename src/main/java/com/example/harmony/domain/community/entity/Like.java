package com.example.harmony.domain.community.entity;

import com.example.harmony.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name="likes")
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "likes")
    private boolean like;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    public Like(boolean like, Post post, User user) {
        this.like = like;
        this.post = post;
        this.user = user;
    }

    public void putLike(boolean like) {
        this.like = like;
    }
}
