package com.als.SMore.notification.service;

import com.als.SMore.notification.dto.NotificationRequestDto;
import com.als.SMore.notification.dto.NotificationResponseDto;
import com.als.SMore.notification.repository.EmitterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 5L * 1000 * 60;  // 기본 타임아웃 설정 - 테스트로 5분.
    private final EmitterRepository emitterRepository;



    /**
     * 클라이언트가 SSE를 구독하는 서비스 메서드.
     * 새로운 SseEmitter를 생성하고 Nginx 버퍼링 방지를 위해 필요한 Http 헤더 설정.
     * 클라이언트가 놓친 이벤트도 함께 전송
     *
     * @param memberPk 로그인한 유저 객체
     * @param lastEventId 유저가 마지막으로 수신한 이벤트의 id
     * @param response http 응답 객체
     * @return 구독한 클라이언트와 연결된 emitter 객체
     */
    public SseEmitter subscribe(Long memberPk, String lastEventId, HttpServletResponse response) {
        String emitterId = makeTimeIncludePk(memberPk);
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        // NGINX PROXY 에서의 필요설정 불필요한 버퍼링방지
        response.setHeader("X-Accel-Buffering", "no");
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Cache-Control", "no-cache");

        //컴플리트, 타임아웃시에는 emitter를 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        emitterRepository.save(emitterId, emitter);

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        sendDummyData(emitterId, emitter, "EventStream Created. [memberPk = " + memberPk + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        resendLostData(lastEventId, memberPk, emitter);

//         Scheduler for sending heartbeat
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.scheduleAtFixedRate(() -> {
//            try {
//                emitter.send(SseEmitter.event().name("ping").data("pong?"));
//            } catch (IOException e) {
//                emitter.completeWithError(e);
//                scheduler.shutdown();
//            }
//        }, 0, 30, TimeUnit.SECONDS); // 30초마다 Ping 메시지 전송
//
//        emitter.onCompletion(scheduler::shutdown);
//        emitter.onTimeout(scheduler::shutdown);

        return emitter;
    }

    /**
     * 회원 ID와 현재 시간을 조합하여 고유한 식별자를 생성하는 메서드
     * 각 회원을 특정 이벤트와 연결시킴
     */
    private String makeTimeIncludePk(Long memberPk) {
        return memberPk + "_" + System.currentTimeMillis();
    }

    /**
     * 클라이언트 연결 초기에 503 에러가 뜨지 않도록 더미 데이터를 전송하는 메서드
     * 연결이 성공적으로 이루어졌는지 확인
     *
     * @param emitterId 해당 클라이언트와 고유하게 연결된 emitter id
     * @param emitter
     * @param eventId
     * @param data 더미 데이터
     */
    private void sendDummyData(String emitterId, SseEmitter emitter, String data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    /**
     * 회원이 놓친 이벤트를 전송하는 메서드
     *
     * @param lastEventId 회원이 마지막으로 수신한 이벤트 id
     * @param member
     * @param emitter
     */
    private void resendLostData(String lastEventId, Long memberPk, SseEmitter emitter) {
        // 놓친 이벤트가 있다면
//        if (!lastEventId.isEmpty()) { // 일단 이벤트 그냥 다 재전송.
            System.out.println("NotificationService.resendLostData1");
            // 멈버 아이디를 기준으로 캐시된 모든 이벤트를 가져온다.
            Map<String, Object> cachedEvents = emitterRepository.findAllEventCacheStartWithByMemberPk(String.valueOf(memberPk));
            // 모든 이벤트를 순회하며
            for (Map.Entry<String, Object> entry : cachedEvents.entrySet()) {
                // lastEventId보다 큰 ID(뒷 시간에 일어난 이벤트) 필터링하여 재전송
                if (lastEventId.compareTo(entry.getKey()) < 0) {
                    try {
                        System.out.println("NotificationService.resendLostData2 " + entry.getValue());
                        emitter.send(SseEmitter.event().id(entry.getKey()).data(new ObjectMapper().writeValueAsString(entry.getValue())));
                    } catch (IOException e) {
                        log.error("Resending lost data failed for memberPk : {}", memberPk, e);
                    }
                }
            }
//        }
    }

    /**
     * 알림 전송 기능을 세 개의 메서드로 나누어 설계.
     * send() : 알림 전송 프로세스를 시작하는 공개 메서드
     * sendNotification() : 저장됨 알림 객체와 함께 클라이언트에게 알림을 전송
     * sendToClient() : 실제로 클라이언트에게 SseEmitter를 통해 알림을 전송
     *
     * @param requestDto 알림에 필요한 정보들을 담은 request dto
     */
    public void send(NotificationRequestDto requestDto) {
//        Notification notification = saveNotification(requestDto);
//        sendNotification(notification);
        String receiverPk = String.valueOf(requestDto.getReceiverPk());
        // 유저의 모든 SseEmitter 가져옴
        Map<String, SseEmitter> emitters = emitterRepository
                .findAllEmitterStartWithByMemberPk(receiverPk);
        emitters.forEach((key, emitter) -> {
            NotificationResponseDto responseDto = NotificationResponseDto.of(requestDto);
            // 데이터 캐시 저장 (유실된 데이터 처리 위함)
            emitterRepository.saveEventCache(key, responseDto);
            // 데이터 전송
            sendToClient(key, responseDto);
        });

    }

    /**
     * 실제로 클라이언트에게 SseEmitter를 통해 알림을 전송하는 메서드
     * emitter를 사용하여 실시간으로 알림 전송
     *
     * @param emitterId 해당 클라이언트와 연결시켜주는 emitter 식별자
     * @param responseDto 알림 응답 객체
     */
    private void sendToClient(String emitterId, NotificationResponseDto responseDto) {
        SseEmitter emitter = emitterRepository.findByEmitterId(emitterId);
        if (emitter != null) {
            try {
                log.info(" 알림 내용 전송 전 =  {}", responseDto.getContent());
                emitter.send(SseEmitter.event().id(emitterId).name("sse").data(responseDto));
//                emitter.send(SseEmitter.event().id(emitterId).name("sse").data(new ObjectMapper().writeValueAsString(responseDto)));
                log.info(" 알림 내용 전송 완료 =  {}", responseDto.getNotificationPk());

            } catch (IOException e) {
//                emitterRepository.deleteById(emitterId);
//                log.error("Failed to send notification", e);
//                throw new RuntimeException("Connection error!");

                if (e.getMessage().contains("Broken pipe")) {
                    log.warn("SSE 연결이 끊어짐. 무시됨.", e); // 경고 메시지로 로그를 기록
                } else {
                    log.info("SSE 연결 오류 발생", e);
                    emitterRepository.deleteById(emitterId);
                    log.warn("삭제 emitter : {}", emitterId);
                }
            }
        } else {
            log.warn("No emitter found for ID: {}", emitterId);
        }
    }


}