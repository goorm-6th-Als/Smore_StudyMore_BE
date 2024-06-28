package com.als.SMore.study.chatting.config;

import com.als.SMore.user.util.TokenProvider;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.*;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketInterceptor implements ChannelInterceptor {

    private final TokenProvider tokenProvider;

    @SneakyThrows
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getCommand() == StompCommand.CONNECT) {
            String authToken = accessor.getFirstNativeHeader("Authorization");
            String validatedToken_UserPk = tokenProvider.validate(authToken);
            if (authToken == null || validatedToken_UserPk==null) {
                log.error("토큰이 없거나 유저정보가 없음 ");
                throw new AuthException("토큰이 없거나 유저정보가 없음 ");
            }

            // UsernamePasswordAuthenticationToken 발급
//            UsernamePasswordAuthenticationToken authentication = tokenProvider.getAuthentication(authToken);
            // accessor에 등록
//            accessor.setUser(authentication);

        }
        return message;
    }
}
