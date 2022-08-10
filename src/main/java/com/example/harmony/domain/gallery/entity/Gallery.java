package com.example.harmony.domain.gallery.entity;

import com.example.harmony.domain.user.entity.Family;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "gallery")
    private List<Image> images;

    @OneToMany(mappedBy = "gallery")
    private List<GalleryComment> comments;

    @ManyToOne
    private Family family;
}
