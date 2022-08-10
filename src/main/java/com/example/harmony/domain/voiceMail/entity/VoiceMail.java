package com.example.harmony.domain.voiceMail.entity;

import com.example.harmony.domain.user.entity.Family;
import com.example.harmony.domain.user.entity.User;
import com.example.harmony.global.common.TimeStamp;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class VoiceMail extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String from;

    private String to;

    private String soundUrl;

    private String soundFilename;

    @ManyToOne
    private User user;

    @ManyToOne
    private Family family;
}
