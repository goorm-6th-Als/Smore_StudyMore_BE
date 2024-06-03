package com.als.SMore.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_pk", nullable = false)
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

    // Getters and Setters
}
