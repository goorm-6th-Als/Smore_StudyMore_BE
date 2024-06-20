package com.als.SMore.user.dto.mystudy.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnterStudy {
    // 유저의 이메일
    private String userId;
    // 신청자 닉네임
    private String nickname;
    // 신청자 프로필 사진
    private String profileURL;
    // 신청서
    private String content;

}
