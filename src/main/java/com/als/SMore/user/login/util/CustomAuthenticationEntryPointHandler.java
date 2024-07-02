package com.als.SMore.user.login.util;

import com.als.SMore.user.mypage.dto.response.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Integer exception = (Integer)request.getAttribute("exception");

        if(exception == null) {
            log.info("이것 때문인가요>?");
            setResponse(response, ErrorCode.UNKNOWN_ERROR,HttpServletResponse.SC_UNAUTHORIZED);
        }
        //잘못된 타입의 토큰인 경우
        else if(exception == 1004) {
            setResponse(response, ErrorCode.WRONG_TYPE_TOKEN,HttpServletResponse.SC_UNAUTHORIZED);
        }
        // 상태 코드 401
        //토큰 만료된 경우
        else if(exception == 1005) {
            setResponse(response, ErrorCode.EXPIRED_TOKEN,HttpServletResponse.SC_UNAUTHORIZED);
        }
        //지원되지 않는 토큰인 경우
        else if(exception == 1006) {
            setResponse(response, ErrorCode.UNSUPPORTED_TOKEN,HttpServletResponse.SC_UNAUTHORIZED);
        }
//        else {
//            setResponse(response, ErrorCode.ACCESS_DENIED,HttpServletResponse.SC_BAD_REQUEST);
//        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode exceptionCode,int httpStatusCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(httpStatusCode);

        response.getWriter().print(new ObjectMapper().writeValueAsString(MessageResponse.builder().message(exceptionCode.getMessage() ).build()));
    }
}
