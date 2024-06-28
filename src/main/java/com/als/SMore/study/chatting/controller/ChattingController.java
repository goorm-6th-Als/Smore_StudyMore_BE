package com.als.SMore.study.chatting.controller;

import com.als.SMore.study.chatting.DTO.ChatMessageRequestDTO;
import com.als.SMore.study.chatting.service.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController

public class ChattingController {

    private final ChattingService chattingService;

    @GetMapping("/chat/{studyPk}/enter")
    public List<Object> getChattingList(@PathVariable("studyPk") String StudyPk){
        log.info("getChattingList , StudyPk = {}",StudyPk);
//        System.out.println(MemberUtil.getUserPk());
//        System.out.println(MemberUtil.getNickname());
        return chattingService.getChattingHistory(StudyPk);
    }


    /**
     * 채팅 메시지 보내는 메서드
     * @param ChatMessage content
     * @param StudyPk 방 id
     * @param message 사용자가 보내는 메시지 내용
     */
    @MessageMapping("/chat/{studyPk}") //클라이언트에서 /pub/chat/{studyPk}로 메시지를 보내면 해당 채팅방을 구독 중인 사용자들에게 메시지를 전달
    public void sendMessage(@DestinationVariable("studyPk") String StudyPk,
                     @Payload ChatMessageRequestDTO requestDTO,
                     Message<?> message) {
        log.info("ChattingController.sendMessage, getContent = {}",requestDTO.getContent());
//        System.out.println(MemberUtil.getUserPk());
//        System.out.println(MemberUtil.getNickname());
        log.info("ChattingController.sendMessage, message = {}",message);
        chattingService.saveAndSendMessage(StudyPk, requestDTO, message);
    }
}