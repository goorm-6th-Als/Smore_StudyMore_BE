package com.als.SMore.study.Chatting.DTO.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChattingRequest {

    private Long senderId;
    private String content;
}