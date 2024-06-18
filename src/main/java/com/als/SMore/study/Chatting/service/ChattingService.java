package com.als.SMore.study.Chatting.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.ChattingRepository;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.study.Chatting.DTO.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChattingService {

    private final SimpMessageSendingOperations messagingTemplate;
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final ChattingRepository chattingRepository;
    private final StudyMemberRepository studyMemberRepository;

    /**
     * 채팅 메시지를 저장하고 처리하는 메서드
     * @param StudyPk 스터디 pk
     * @param chatMessage 저장할 채팅 메시지
     * @param message WebSocket 메시지
     */
    public void saveAndSendMessage(String StudyPk, ChatMessage chatMessage, Message<?> message) {

        Optional<Study> studyOptional = studyRepository.findById(Long.parseLong(StudyPk));
        //유저디테일에서 받은 토큰 기반 정보로 변경
        Optional<Member> memberOptional = memberRepository.findById(Long.parseLong(chatMessage.getMemberPk()));

        if(studyOptional.isPresent() && memberOptional.isPresent()){
            Optional<StudyMember> studyMember = studyMemberRepository.findByMemberAndStudy(memberOptional.get(), studyOptional.get());

            if(studyMember.isPresent()){
                //메세지 Id 생성
                String messageId = UUID.randomUUID().toString();
                String studyPkString = String.valueOf(studyOptional.get().getStudyPk());
                String memberPkString = String.valueOf(studyMember.get().getMember().getMemberPk());
                String nickName = studyMember.get().getMember().getNickName();
                String profileImg = studyMember.get().getMember().getProfileImg();

                chatMessage.setMessageId(messageId);
                chatMessage.setStudyPk(studyPkString);
                chatMessage.setMemberPk(memberPkString);
                chatMessage.setMemberName(nickName);
                chatMessage.setTime(LocalDateTime.now());
                chatMessage.setProfileImageUrl(profileImg);

                // WebSocket 세션에 속성 저장
                StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
                headerAccessor.getSessionAttributes().put("studyPk", studyPkString);
                headerAccessor.getSessionAttributes().put("memberPk", memberPkString);
                headerAccessor.getSessionAttributes().put("nickname", nickName);
                headerAccessor.getSessionAttributes().put("profileImageUrl", profileImg);

                // Redis에 채팅 메시지 저장 및 발송
                chattingRepository.saveChatMessage(studyPkString, chatMessage);
                messagingTemplate.convertAndSend("/sub/chat/" + studyPkString, chatMessage);

            }
        }
    }

    public void getChattinghistory(String studyPk) {
        List<Object> chatHistory = chattingRepository.getChatHistory(studyPk);
        messagingTemplate.convertAndSend(String.format("/sub/chat/challenge/%s", studyPk), chatHistory);
    }
}
