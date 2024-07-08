package com.als.SMore.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_token")
@Getter
@Builder(toBuilder = true)
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

    @Column(name = "kakao_access_token", nullable = false)
    private String kakaoAccessToken;

    @Column(name = "kakao_refresh_token", nullable = false)
    private String kakaoRefreshToken;
}