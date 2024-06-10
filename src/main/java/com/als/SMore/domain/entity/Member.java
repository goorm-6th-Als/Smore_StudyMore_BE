package com.als.SMore.domain.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "member")
@Getter @Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @Tsid
    @Column(name = "member_pk")
    private Long memberPk;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "kakao_user_id")
    private String KakaoUserId;
}
