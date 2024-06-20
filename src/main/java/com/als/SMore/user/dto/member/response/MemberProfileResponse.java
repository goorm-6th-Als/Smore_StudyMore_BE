package com.als.SMore.user.dto.member.response;

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
