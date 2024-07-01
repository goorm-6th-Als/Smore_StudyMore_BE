package com.als.SMore.study.chatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class SecurityWebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry message) {
        message
                .nullDestMatcher().permitAll() //CONNECT, HEARTBEAT 등은 permitAll()로 열여준다
                .simpDestMatchers("/pub/**").authenticated() //destination이 /pub/** 인 메세지는 인증 된 사용자(authenticated())만 전송 가능
                .simpSubscribeDestMatchers("/sub/**").authenticated()//인증 된 사용자만 /sub/** subscribe 가능
                .anyMessage().denyAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true; //개발 중 CSRF 비활성화를 위해 true로 설정

    }
}
