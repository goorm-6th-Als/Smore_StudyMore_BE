package com.als.SMore.study.Chatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // 메시지 브로커가 지원하는 WebSocket 메시지 처리를 활성화
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { //핸드쉐이크 통신을 담당할 엔드 포인트
        registry.addEndpoint( "/study") //WebSocket 연결 시 요청을 보낼 EndPoint
                .setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {  // 메모리 기반의 Simple Message Broker 활성화
        registry.enableSimpleBroker("/sub");  // 메시지 브로커가 Subscriber들에게 메시지를 전달할 URL 지정(메시지 구독 요청)
        registry.setApplicationDestinationPrefixes("/pub"); // 클라이언트가 서버로 메시지 보낼 URL 접두사 지정(메시지 발행 요청)
    }
}
