package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_token")
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberToken {

    @Id
    @Column(name = "member_pk")
    private Long member_pk;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId // Member의 ID를 이 엔티티의 ID로 사용
    @JoinColumn(name = "member_pk")
    private Member member;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
}