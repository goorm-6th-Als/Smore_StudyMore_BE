package com.als.SMore.notification.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
public class NotificationResponseDto {
    private UUID notificationPk;
    private String studyPk;
    private String receiverPk;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time;

    public NotificationResponseDto(NotificationRequestDto requestDto) {
        this.notificationPk = UUID.randomUUID();
        this.studyPk = String.valueOf(requestDto.getStudyPk());
        this.receiverPk = String.valueOf(requestDto.getReceiverPk());
        this.content = requestDto.getContent();
        this.time = LocalDateTime.now();
    }

    public static NotificationResponseDto of(NotificationRequestDto requestDto) {
        return new NotificationResponseDto(requestDto);
    }
}
