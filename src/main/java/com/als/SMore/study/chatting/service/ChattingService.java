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
import com.als.SMore.user.login.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
//    public void saveAndSendMessage1(String StudyPk, ChatMessageRequestDTO requestDTO, Message<?> message) {
//        Long MemberPk = MemberUtil.getUserPk();
//
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
//        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
//        String sessionStudyPk = (String) sessionAttributes.get("studyPk");
//        String sessionMemberPk = (String) sessionAttributes.get("memberPk");
//
//        if(sessionStudyPk==null || sessionMemberPk==null || !sessionStudyPk.equals(StudyPk) || !sessionMemberPk.equals(String.valueOf(MemberPk))){
//            //처음인 경우 -> DB 조회 검증 후 세션 정보까지 저장 후 메세지 발송
//            sendMessageInitial1(StudyPk, requestDTO, message, MemberPk);
//
//        }else if(sessionStudyPk.equals(StudyPk) && sessionMemberPk.equals(String.valueOf(MemberPk))) {
//            //처음이 아니어서 DB 조회 생략 해도 되는 경우 발송
//            sendMessage1(StudyPk, requestDTO, message, MemberPk);
//        }
//    }
//
//    private void sendMessageInitial1(String StudyPk, ChatMessageRequestDTO requestDTO, Message<?> message, Long MemberPk) {
//        Optional<Study> studyOptional = studyRepository.findById(Long.parseLong(StudyPk));
//        Optional<Member> memberOptional = memberRepository.findById(MemberPk);
//
//        if(studyOptional.isPresent() && memberOptional.isPresent()){
//            Optional<StudyMember> studyMember = studyMemberRepository.findByMemberAndStudy(memberOptional.get(), studyOptional.get());
//            if(studyMember.isPresent()){
//                log.info("saveAndSendMessage1 채팅방에 속한 유저가 맞음 - studyMember.isPresent");
//
//                //메세지 Id 생성
//                String messageId = UUID.randomUUID().toString();
//                String studyPk = String.valueOf(studyOptional.get().getStudyPk());
//                String memberPk = String.valueOf(studyMember.get().getMember().getMemberPk());
//                String nickName = studyMember.get().getMember().getNickName();
//                String profileImg = studyMember.get().getMember().getProfileImg();
//
//
//                //request로 받은 DTO에서 DB 저장, 리스폰스용 객체로 매핑
//                ChatMessage chatMessage = ChatMessage.builder()
//                        .messagePk(messageId)
//                        .studyPk(studyPk)
//                        .memberPk(memberPk)
//                        .memberName(nickName)
//                        .content(requestDTO.getContent())
//                        .time(LocalDateTime.now())
//                        .profileImageUrl(profileImg)
//                        .build();
//
//                // WebSocket 세션에 속성 저장
//                StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
//                headerAccessor.getSessionAttributes().put("studyPk", studyPk);
//                headerAccessor.getSessionAttributes().put("memberPk", memberPk);
//                headerAccessor.getSessionAttributes().put("nickname", nickName);
//                headerAccessor.getSessionAttributes().put("profileImageUrl", profileImg);
//
//                saveAndSend1(studyPk, chatMessage);
//            }
//        }
//    }
//
//    private void sendMessage1(String StudyPk, ChatMessageRequestDTO requestDTO, Message<?> message, Long MemberPk) {
//
//                log.info("saveAndSendMessage1 채팅방에 속한 유저가 맞음 - studyMember.isPresent");
//
//                //메세지 Id 생성
//                String messageId = UUID.randomUUID().toString();
//                String memberPk = String.valueOf(MemberPk);
//                String nickName = MemberUtil.getNickname();
//                String profileImg =MemberUtil.getProfileUrl();
//
//                //request로 받은 DTO에서 DB 저장, 리스폰스용 객체로 매핑
//                ChatMessage chatMessage = ChatMessage.builder()
//                        .messagePk(messageId)
//                        .studyPk(StudyPk)
//                        .memberPk(memberPk)
//                        .memberName(nickName)
//                        .content(requestDTO.getContent())
//                        .time(LocalDateTime.now())
//                        .profileImageUrl(profileImg)
//                        .build();
//
//                // WebSocket 세션에 속성 저장
//                StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
//                headerAccessor.getSessionAttributes().put("studyPk", StudyPk);
//                headerAccessor.getSessionAttributes().put("memberPk", memberPk);
//                headerAccessor.getSessionAttributes().put("nickname", nickName);
//                headerAccessor.getSessionAttributes().put("profileImageUrl", profileImg);
//
//                saveAndSend(StudyPk, chatMessage);
//            }
//
//
//    private void saveAndSend1(String studyPk, ChatMessage chatMessage) {
//        // Redis에 채팅 메시지 저장 및 발송
//        chattingRepository.saveChatMessage(studyPk, chatMessage);
//        messagingTemplate.convertAndSend("/sub/chat/" + studyPk, chatMessage);
//        log.info("채팅 저장 및 전송 완료");
//    }

    public void saveAndSendMessage(String StudyPk, ChatMessageRequestDTO requestDTO, Message<?> message) {
        Long MemberPk = MemberUtil.getUserPk();

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        String sessionStudyPk = (String) sessionAttributes.get("studyPk");
        String sessionMemberPk = (String) sessionAttributes.get("memberPk");

        if (sessionStudyPk == null || sessionMemberPk == null || !sessionStudyPk.equals(StudyPk) || !sessionMemberPk.equals(String.valueOf(MemberPk))) {
            //헤더에 정보가 없는 경우 -> DB에 접근하여 스터디와 멤버의 정보 조회.
            initialMessage(StudyPk, requestDTO, MemberPk, headerAccessor);
        } else {
            //정보가 있는 경우 -> 추가적인 DB접근 없이 메세지 처리
            uninitialMessage(StudyPk, requestDTO, MemberPk, headerAccessor);
        }
    }

    private void initialMessage(String StudyPk, ChatMessageRequestDTO requestDTO, Long MemberPk, StompHeaderAccessor headerAccessor) {
        Optional<Study> studyOptional = studyRepository.findById(Long.parseLong(StudyPk));
        Optional<Member> memberOptional = memberRepository.findById(MemberPk);

        if (studyOptional.isPresent() && memberOptional.isPresent()) {
            Optional<StudyMember> studyMember = studyMemberRepository.findByMemberAndStudy(memberOptional.get(), studyOptional.get());
            if (studyMember.isPresent()) {
                ChatMessage chatMessage = createChatMessage(
                        studyOptional.get().getStudyPk().toString(),
                        studyMember.get().getMember().getMemberPk().toString(),
                        studyMember.get().getMember().getNickName(),
                        studyMember.get().getMember().getProfileImg(),
                        requestDTO
                );
                updateSessionAttributes(headerAccessor, chatMessage);
                saveAndSend(chatMessage.getStudyPk(), chatMessage);
            } else {
                log.info("스터디에 해당하는 멤버가 아님.");
            }
        } else {
            log.info("스터디 정보가 없거나 멤버 정보가 없음.");
        }
    }

    private void uninitialMessage(String StudyPk, ChatMessageRequestDTO requestDTO, Long MemberPk, StompHeaderAccessor headerAccessor) {
        ChatMessage chatMessage = createChatMessage(
                StudyPk,
                String.valueOf(MemberPk),
                MemberUtil.getNickname(),
                MemberUtil.getProfileUrl(),  //MemberUtil에서 멤버정보 가져오는 부분들 전부 바꿔야함.
                requestDTO
        );
        updateSessionAttributes(headerAccessor, chatMessage);
        saveAndSend(StudyPk, chatMessage);
    }

    private ChatMessage createChatMessage(String studyPk, String memberPk, String nickName, String profileImg, ChatMessageRequestDTO requestDTO) {
        String messageId = UUID.randomUUID().toString();
        return ChatMessage.builder()
                .messagePk(messageId)
                .studyPk(studyPk)
                .memberPk(memberPk)
                .memberName(nickName)
                .content(requestDTO.getContent())
                .time(LocalDateTime.now())
                .profileImageUrl(profileImg)
                .build();
    }

    private void updateSessionAttributes(StompHeaderAccessor headerAccessor, ChatMessage chatMessage) {
        headerAccessor.getSessionAttributes().put("studyPk", chatMessage.getStudyPk());
        headerAccessor.getSessionAttributes().put("memberPk", chatMessage.getMemberPk());
        headerAccessor.getSessionAttributes().put("nickname", chatMessage.getMemberName());
        headerAccessor.getSessionAttributes().put("profileImageUrl", chatMessage.getProfileImageUrl());
    }

    private void saveAndSend(String studyPk, ChatMessage chatMessage) {
        chattingRepository.saveChatMessage(studyPk, chatMessage);
        messagingTemplate.convertAndSend("/sub/chat/" + studyPk, chatMessage);
        log.info("채팅 저장 및 전송 완료");
    }

    public List<Object> getChattingHistory(String studyPk) {
        List<Object> chatHistory = chattingRepository.getChatHistory(studyPk);
        log.info("ChattingService.getChattingHistory chatHistory = {}", chatHistory);
//        messagingTemplate.convertAndSend("/sub/chat/challenge/" + studyPk, chatHistory);
        return chatHistory;
    }
}
