package com.als.SMore.study.chatting.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.ChattingRepository;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.study.chatting.DTO.ChatMessage;
import com.als.SMore.study.chatting.DTO.ChatMessageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
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
     * @param requestDTO 저장할 채팅 메시지
     * @param message WebSocket 메시지
     */
    public void saveAndSendMessage(String StudyPk, ChatMessageRequestDTO requestDTO, Message<?> message) {

        Optional<Study> studyOptional = studyRepository.findById(Long.parseLong(StudyPk));
        //유저디테일에서 받은 토큰 기반 정보로 변경
        Optional<Member> memberOptional = memberRepository.findById(Long.parseLong(requestDTO.getMemberPk()));

        if(studyOptional.isPresent() && memberOptional.isPresent()){
            Optional<StudyMember> studyMember = studyMemberRepository.findByMemberAndStudy(memberOptional.get(), studyOptional.get());
            if(studyMember.isPresent()){
                System.out.println("ChattingService.saveAndSendMessage1");
                //메세지 Id 생성
                String messageId = UUID.randomUUID().toString();
                String studyPk = String.valueOf(studyOptional.get().getStudyPk());
                String memberPk = String.valueOf(studyMember.get().getMember().getMemberPk());
                String nickName = studyMember.get().getMember().getNickName();
                String profileImg = studyMember.get().getMember().getProfileImg();

                //request로 받은 DTO에서 DB 저장, 리스폰스용 객체로 매핑
                // -> 이 부분 sett 말고 나중에 빌더나 생성자 초기화로 변경하는게 코드간결해질듯.
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setMessageId(messageId);
                chatMessage.setStudyPk(studyPk);
                chatMessage.setMemberPk(memberPk);
                chatMessage.setMemberName(nickName);
                chatMessage.setContent(requestDTO.getContent());
                chatMessage.setTime(LocalDateTime.now());
                chatMessage.setProfileImageUrl(profileImg);

                // WebSocket 세션에 속성 저장
                StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
                headerAccessor.getSessionAttributes().put("studyPk", studyPk);
                headerAccessor.getSessionAttributes().put("memberPk", memberPk);
                headerAccessor.getSessionAttributes().put("nickname", nickName);
                headerAccessor.getSessionAttributes().put("profileImageUrl", profileImg);

                // Redis에 채팅 메시지 저장 및 발송
                chattingRepository.saveChatMessage(studyPk, chatMessage);
                messagingTemplate.convertAndSend("/sub/chat/" + studyPk, chatMessage);
                System.out.println("ChattingService.saveAndSendMessage2");

            }
        }
    }

    public List<Object> getChattingHistory(String studyPk) {
        List<Object> chatHistory = chattingRepository.getChatHistory(studyPk);
        log.info("ChattingService.getChattingHistory chatHistory = {}", chatHistory);
//        messagingTemplate.convertAndSend("/sub/chat/challenge/" + studyPk, chatHistory);
        return chatHistory;

    }
}
