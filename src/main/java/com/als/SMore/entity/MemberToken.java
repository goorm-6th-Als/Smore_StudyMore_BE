package com.als.SMore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "member_token")
public class MemberToken {

    @Id
    @Column(name = "member_pk")
    private Long memberPk;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @OneToOne
    @MapsId
    @JoinColumn(name = "member_pk")
    private Member member;

    // Getters and Setters
}
