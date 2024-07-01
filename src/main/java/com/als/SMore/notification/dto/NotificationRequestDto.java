package com.als.SMore.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationRequestDto {
    private Long studyPk;
    private Long receiverPk;
    private String content;
}
