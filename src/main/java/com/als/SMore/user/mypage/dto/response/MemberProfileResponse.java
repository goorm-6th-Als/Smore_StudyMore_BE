package com.als.SMore.user.mypage.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class MemberProfileResponse {
    private String nickname;
    private String profileImage;
}
