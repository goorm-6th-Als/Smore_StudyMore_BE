package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "member_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
