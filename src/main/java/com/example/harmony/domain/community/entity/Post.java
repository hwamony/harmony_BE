package com.example.harmony.domain.community.entity;

import com.example.harmony.domain.user.entity.User;
import com.example.harmony.global.common.TimeStamp;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Post extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "post")
    private List<Tag> tags;

    private String imageUrl;

    private String imageFilename;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post")
    List<PostComment> comments;
}
