package com.als.SMore.notification.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NotificationResponseDto {
    private UUID notificationPk;
    private String studyPk;
    private String receiverPk;
    private String content;

    public NotificationResponseDto(NotificationRequestDto requestDto) {
        this.notificationPk = UUID.randomUUID();
        this.studyPk = String.valueOf(requestDto.getStudyPk());
        this.receiverPk = String.valueOf(requestDto.getReceiverPk());
        this.content = requestDto.getContent();
    }

    public static NotificationResponseDto of(NotificationRequestDto requestDto) {
        return new NotificationResponseDto(requestDto);
    }
}
