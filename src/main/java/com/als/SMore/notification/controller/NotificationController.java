package com.als.SMore.notification.controller;

import com.als.SMore.notification.service.NotificationService;
import com.als.SMore.user.util.MemberUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 알림 관련 요청 처리
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 클라이언트의 실시간 알림 구독 요청을 처리하고 Server-Sent Events(SSE) 스트림 반환.
     *
     * @param lastEventId 클라이언트가 마지막으로 수신한 이벤트의 ID
     * @param response    HTTP 응답 객체
     * @return Server-Sent Events(SSE) 스트림을 포함하는 ResponseEntity
     */
    @GetMapping(value = "/subscribe/notification", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                HttpServletResponse response) {
        Long userPk = MemberUtil.getUserPk();
        log.info("userPk = {}, Last-Event-ID = {}", userPk, lastEventId);
        return notificationService.subscribe(userPk, lastEventId, response);
    }
}
