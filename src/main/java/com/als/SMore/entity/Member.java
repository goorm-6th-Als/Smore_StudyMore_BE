package com.als.SMore.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_pk")
    private Long memberPk;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @OneToMany(mappedBy = "member")
    private Set<Study> studies;

    @OneToMany(mappedBy = "member")
    private Set<StudyBoard> studyBoards;

    // Getters and Setters
}
