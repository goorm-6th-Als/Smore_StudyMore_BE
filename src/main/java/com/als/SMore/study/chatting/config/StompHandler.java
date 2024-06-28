package com.als.SMore.study.chatting.config;

import com.als.SMore.user.util.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.*;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.*;
import org.springframework.stereotype.Component;

@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {
    private final TokenProvider  tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(accessor.getCommand() == StompCommand.CONNECT) {

            String authorization = accessor.getFirstNativeHeader("Authorization");

            if (authorization != null && authorization.startsWith("Bearer")){
                String token = authorization.substring(7);

                try {
                    String userPk = tokenProvider.validate(token);
                    if (userPk != null) {
                        log.info("userID : {}", userPk);
                        accessor.addNativeHeader("userInfo",userPk);
                    }
                }catch (SecurityException | MalformedJwtException e) {
                    log.info("JWT가 올바르게 구성되지 않았습니다.");
                    throw new MessageDeliveryException("jwt body error");
                } catch (ExpiredJwtException e) {
                    log.info("JWT가 만료됨");
                    throw new MessageDeliveryException("만료됨");
                } catch (UnsupportedJwtException e) {
                    log.info("지원되지 않는 JWT");
                    throw new MessageDeliveryException("지원하지 않는 형식");
                } catch (IllegalArgumentException e) {
                    log.info("JWT의 클래엠이 null 또는 비어 있음");
                    throw new MessageDeliveryException("비어있음");
                } catch (Exception e) {
                    throw new MessageDeliveryException("에러");
                }
            }
        }
        return message;
    }
}
