package com.als.SMore.user.login.util.aop;

import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.user.login.util.TokenProvider;
import com.als.SMore.user.login.util.aop.annotation.JwtRole;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Order
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class JwtRoleAop {

    private final TokenProvider tokenProvider;

    @Around("@annotation(com.als.SMore.user.login.util.aop.annotation.JwtRole)")
    public Object isCheckedRole(final ProceedingJoinPoint joinPoint) throws Throwable {
        // request 랑 response 를 먼저 생성
        log.info("부분 AOP");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // 토큰을 받아오기
        String token = request.getHeader("authorization").substring(7);

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        JwtRole custom = methodSignature.getMethod().getAnnotation(JwtRole.class);

        // URI 에서 studyPk를 가져옴
        String studyPk = request.getServletPath().split("/")[custom.index()];

        // tokenProvider 에서 토큰에 studyPk에 관한 역할이 있는 지 판단함.
//        if(!tokenProvider.isCheckedRole(token,studyPk)){
//            // 인터셉터를 만들어서 연결을 끊어 버리자
//            //setResponse(response,"study에대한 접근 권한이 없습니다", 403);
//            throw new CustomException(CustomErrorCode.NOT_FOUND_STUDY_PK);
//        }

        String role = tokenProvider.getRole(token, studyPk);
        if(!role.equals("admin")){
            throw new CustomException(CustomErrorCode.NOT_FOUND_ROLE);
        }

        log.info("부분 AOP 끝남");
        return joinPoint.proceed();
    }
}
