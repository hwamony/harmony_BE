package com.example.harmony.domain.community.model;

import com.example.harmony.domain.user.model.User;
import com.example.harmony.global.common.TimeStamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Entity
public class PostComment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    public PostComment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public void putComment(String content) {
        this.content = content;
    }
}
