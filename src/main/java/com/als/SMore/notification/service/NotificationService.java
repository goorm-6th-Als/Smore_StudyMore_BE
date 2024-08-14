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
    private static final Long DEFAULT_TIMEOUT = 30L * 1000 * 60;  // 기본 타임아웃 설정
    private final EmitterRepository emitterRepository;

    public SseEmitter subscribe(Long memberPk, String lastEventId, HttpServletResponse response) {

        String emitterId = makeTimeIncludePk(memberPk);

        //emitter 기존것이 존재하면 없애고 구독 설정
        Map<String, SseEmitter> existingEmitters = emitterRepository.findAllEmitterStartWithByMemberPk(String.valueOf(memberPk));
        existingEmitters.forEach((key, emitter) -> emitterRepository.deleteById(key));

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

    private String makeTimeIncludePk(Long memberPk) {
        return memberPk + "_" + System.currentTimeMillis();
    }


    private void sendDummyData(String emitterId, SseEmitter emitter, String data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private void resendLostData(String lastEventId, Long memberPk, SseEmitter emitter) {
        // 놓친 이벤트가 있다면
        if (!lastEventId.isEmpty()) { // 일단 이벤트 그냥 다 재전송.
            System.out.println("NotificationService.resendLostData1");
            // 멈버 아이디를 기준으로 캐시된 모든 이벤트를 가져온다.
            Map<String, Object> cachedEvents = emitterRepository.findAllEventCacheStartWithByMemberPk(String.valueOf(memberPk));
            // 모든 이벤트를 순회하며
            for (Map.Entry<String, Object> entry : cachedEvents.entrySet()) {
                // lastEventId보다 큰 ID(뒷 시간에 일어난 이벤트) 필터링하여 재전송
                if (lastEventId.compareTo(entry.getKey()) < 0) {
                    try {
                        System.out.println("NotificationService.resendLostData2 " + entry.getValue());
                        emitter.send(SseEmitter.event().id(entry.getKey()).name("sse").data(entry.getValue()));
                    } catch (IOException e) {
                        log.error("Resending lost data failed for memberPk : {}", memberPk, e);
                    }
                }
            }
        }
    }


    public void send(NotificationRequestDto requestDto) {
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


    private void sendToClient(String emitterId, NotificationResponseDto responseDto) {
        SseEmitter emitter = emitterRepository.findByEmitterId(emitterId);
        if (emitter != null) {
            try {
                log.info(" 알림 내용 전송 전 =  {}", responseDto.getContent());
                emitter.send(SseEmitter.event().id(emitterId).name("sse").data(responseDto));
                System.out.println(emitter);
//                emitter.send(SseEmitter.event().id(emitterId).name("sse").data(new ObjectMapper().writeValueAsString(responseDto)));
                log.info(" 알림 내용 전송 완료 =  {}", responseDto.getNotificationPk());

            } catch (Exception e) {
//                emitterRepository.deleteById(emitterId);
//                log.error("Failed to send notification", e);
//                throw new RuntimeException("Connection error!");

                if (e.getMessage().contains("Broken pipe")) {
                    log.warn("SSE 연결이 끊어짐. 무시됨.", e); // 경고 메시지로 로그를 기록
                } else {
                    log.info("SSE 연결 오류 발생", e);
//                    emitterRepository.deleteById(emitterId);
//                    log.warn("삭제 emitter : {}", emitterId);
                }
            }
        } else {
            log.warn("No emitter found for ID: {}", emitterId);
        }
    }

}