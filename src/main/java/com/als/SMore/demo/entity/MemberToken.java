package com.als.SMore.demo.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

@Entity
@Table(name = "member_token")
public class MemberToken {
    @Id @Tsid
    @Column(name = "member_pk", nullable = false)
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
