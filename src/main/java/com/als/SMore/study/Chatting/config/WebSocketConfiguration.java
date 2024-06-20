package com.als.SMore.study.Chatting.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Slf4j
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker // 메시지 브로커가 지원하는 WebSocket 메시지 처리를 활성화
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { //핸드쉐이크 통신을 담당할 스톰프 엔드 포인트
        registry.addEndpoint( "/con") //WebSocket 연결 시 요청을 보낼 EndPoint
                .setAllowedOriginPatterns("*")
                .withSockJS() // websocket 관련 자바스크립트 라이브러리 SockJS 설정
                .setHeartbeatTime(10000); // 클라이언트 - 서버 연결 상태 확인 주기 : 10초;
    }

    @Override //메시지 브로커 설정
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");  // 메시지 브로커가 Subscriber들에게(프론트로) 메시지를 전달할 URL 지정
        registry.setApplicationDestinationPrefixes("/pub"); // 송신 메시지를 전송하는 데 사용되는 URL
    }

    @Override // 웹소켓 전송 설정 조정
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(64 * 1024); // 메시지 최대 크기 default : 64 * 1024
        registration.setSendTimeLimit(10 * 10000); // 메시지 전송 시간 default : 10 * 10000
        registration.setSendBufferSizeLimit(512 * 1024); // 버퍼 사이즈 default : 512 * 1024
    }
}
