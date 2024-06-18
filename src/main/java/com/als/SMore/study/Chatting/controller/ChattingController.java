package com.als.SMore.study.Chatting.controller;

import com.als.SMore.study.Chatting.DTO.ChatMessage;
import com.als.SMore.study.Chatting.service.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChattingController {

    private final ChattingService chattingService;

    @GetMapping("/sub/chat/{studyPk}/enter")
    public void getChattings(@PathVariable("studyPk") String StudyPk){
        chattingService.getChattinghistory(StudyPk);
    }


    /**
     * 채팅 메시지 보내는 메서드
     * @param ChatMessage messageId, studyPk, memberPk, memberName, content, time, profileImageUrl
     * @param StudyPk 방 id
     * @param message 사용자가 보내는 메시지 내용
     */
    @MessageMapping("/chat/{studyPk}") //클라이언트에서 /pub/chat/{studyPk}로 메시지를 보내면 해당 채팅방을 구독 중인 사용자들에게 메시지를 전달
    public void sendMessage(@DestinationVariable("studyPk") String StudyPk,  //@DestinationVariable : 구독 및 발행 URL의 경로변수를 지정
                     @Payload ChatMessage chatMessage,
                     Message<?> message) {
        //유저 디테일에서 Pk, name, profileImage 받아서 인자에 추가.

        chattingService.saveAndSendMessage(StudyPk, chatMessage, message);
    }
}