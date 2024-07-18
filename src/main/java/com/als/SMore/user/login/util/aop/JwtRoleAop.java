package com.als.SMore.user.login.util.aop;

import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import com.als.SMore.user.login.util.TokenProvider;
import com.als.SMore.user.login.util.aop.dto.AopDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Order
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class JwtRoleAop extends BasicJwtAop{

    private final TokenProvider tokenProvider;

    @Around("@annotation(com.als.SMore.user.login.util.aop.annotation.JwtRole)")
    public Object isCheckedRole(final ProceedingJoinPoint joinPoint) throws Throwable {
        // request 랑 response 를 먼저 생성
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        AopDto aopDto = super.getAopDto(request);

        // 토큰을 받아오기
        String token = aopDto.getToken();

        // URI 에서 studyPk를 가져옴
        String studyPk = aopDto.getStudyPk();

        String role = tokenProvider.getRole(token, studyPk);
        if(!role.equals("admin")){
            throw new CustomException(CustomErrorCode.NOT_FOUND_ROLE);
        }

        return joinPoint.proceed();
    }
}
