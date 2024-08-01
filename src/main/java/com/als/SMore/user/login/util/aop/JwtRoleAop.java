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

import java.util.Map;

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
        String userPk = String.valueOf(MemberUtil.getUserPk());

        String studyPk = super.getStudyPk(request);

        Map<String, String> attribute = (Map<String, String>) request.getSession().getAttribute(userPk);
        String role = attribute.get(studyPk);

        if(!role.equals("admin")){
            throw new CustomException(CustomErrorCode.NOT_FOUND_ROLE);
        }

        return joinPoint.proceed();
    }
}
