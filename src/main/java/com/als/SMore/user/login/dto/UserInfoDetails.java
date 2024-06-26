package com.als.SMore.user.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoDetails {
    private Long userPk;
    private String userId;
    private String profileImg;
    private String nickName;
    private String fullName;
    private String kakaoAccessToken;
}
