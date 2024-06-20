package com.als.SMore.user.dto.mystudy.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  IsCheckedStatusRequest {
    // 스터디의 pk
    private Long studyPk;
    // 유저의 이메일
    private String userId;
}
