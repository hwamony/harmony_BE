package com.example.harmony.domain.schedule.entity;

import com.example.harmony.domain.gallery.entity.Gallery;
import com.example.harmony.domain.user.entity.Family;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: enum? String?
    private String category;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String content;

    private boolean done;

    @OneToOne
    private Gallery gallery;

    @ManyToOne
    private Family family;
}
