package com.als.SMore.user.login.util.aop;

import com.als.SMore.domain.entity.MemberToken;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.MemberTokenRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.global.JwtAuthException;
import com.als.SMore.user.login.util.MemberUtil;
import com.als.SMore.user.login.util.TokenProvider;
import com.als.SMore.user.login.util.aop.annotation.JwtRole;
import com.als.SMore.user.login.util.aop.dto.AopDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Slf4j
@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
public class JwtGlobalAop extends BasicJwtAop{

    private final TokenProvider tokenProvider;
    private final StudyMemberRepository studyMemberRepository;

    @Around("controllerPointcut() " +
            "&& !@annotation(com.als.SMore.user.login.util.aop.annotation.NotAop) ")
    private Object isCheckedStudyPkToAccessToken(final ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        Long memberPk = MemberUtil.getUserPk();

        AopDto aopDto = super.getAopDto(request);
        String studyPk = aopDto.getStudyPk();
        String token = aopDto.getToken();

        // tokenProvider 에서 토큰에 studyPk에 관한 역할이 있는 지 판단함.
        if(!tokenProvider.isCheckedRole(aopDto.getToken(), studyPk)){
            // 인터셉터를 만들어서 연결을 끊어 버리자
            //setResponse(response,"study에대한 접근 권한이 없습니다", 403);
            StudyMember studyMember = studyMemberRepository.findByStudyStudyPkAndMemberMemberPk(
                    Long.parseLong(studyPk), memberPk
            ).orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY_PK));

            // studypk와 role 을 가지고 와야 한다.
            String role = studyMember.getRole();
            String renewToken = tokenProvider.createRenewToken(token, Long.parseLong(studyPk), role);
            throw new JwtAuthException(CustomErrorCode.JWT_AUTH_CODE,renewToken);
        }

        //log.info("닉네임 정보 : {}",nickname);
        System.out.println(request.getMethod()+" : "+request.getServletPath());
        return joinPoint.proceed();
    }

    @Pointcut("execution(* com.als.SMore.study.*.controller.*.*(..))")
    public void controllerPointcut(){}
}
