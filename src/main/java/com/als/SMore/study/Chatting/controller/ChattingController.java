package com.als.SMore.study.Chatting.controller;

import com.als.SMore.study.Chatting.DTO.request.ChattingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChattingController {

    private final SimpMessagingTemplate simpMessagingTemplate;  // @EnableWebSocketMessageBroker를 통해 등록되는 Bean으로, Broker로 메시지 전달
//    private final
    @MessageMapping("/{RoomId}/chat")  // 클라이언트가 SEND할 수 있는 경로.
    // WebSocketConfig에서 등록한 applicationDestinationPrfixes와 @MessageMapping의 경로가 합쳐진다.
    public void chat(@DestinationVariable Long RoomId, ChattingRequest chattingRequest) { //@DestinationVariable : 구독 및 발행 URL의 경로변수를 지정
        //클라이언트에서 /publication/chattings/{chattingRoomId}/messages로 메시지를 보내면 해당 채팅방을 구독 중인 사용자들에게 메시지를 전달
        log.info("Message [{}] send by member: {} to chatting room: {}", chattingRequest.getContent(), chattingRequest.getSenderId(), RoomId);

        simpMessagingTemplate.convertAndSend("/sub/" + RoomId, chattingRequest.getContent());

    }
}