package com.als.SMore.user.login.util;

import com.als.SMore.domain.entity.MemberToken;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.MemberTokenRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.global.JwtAuthException;
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
@Component
@RequiredArgsConstructor
public class JwtAop {

    private final TokenProvider tokenProvider;
    private final MemberTokenRepository memberTokenRepository;
    private final StudyMemberRepository studyMemberRepository;

    @Around("controllerPointcut() " +
            "&& !@annotation(com.als.SMore.user.login.util.NotAop) " +
            "&& !@annotation(com.als.SMore.user.login.util.JwtAuthority)")
    private Object isCheckedStudyPkToAccessToken(final ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // 토큰을 받아오기
        String token = request.getHeader("authorization").substring(7);
        Long memberPk = MemberUtil.getUserPk();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        JwtRole custom = methodSignature.getMethod().getAnnotation(JwtRole.class);

        int index = 2;
        if(custom != null){
            index = custom.index();
        }

        // URI 에서 studyPk를 가져옴
        String studyPk = request.getServletPath().split("/")[index];
        log.info("스터디 pk : {}",studyPk);

        // tokenProvider 에서 토큰에 studyPk에 관한 역할이 있는 지 판단함.
        if(!tokenProvider.isCheckedRole(token,studyPk)){
            // 인터셉터를 만들어서 연결을 끊어 버리자
            //setResponse(response,"study에대한 접근 권한이 없습니다", 403);
            StudyMember studyMember = studyMemberRepository.findByStudyStudyPkAndMemberMemberPk(
                    Long.parseLong(studyPk), memberPk
            ).orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY_PK));

            // studypk와 role 을 가지고 와야 한다.
            String role = studyMember.getRole();
            String renewToken = tokenProvider.createRenewToken(token, Long.parseLong(studyPk), role);
            MemberToken memberToken = memberTokenRepository.findMemberTokenByMember_MemberPk(memberPk)
                    .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY_PK));
            memberToken = memberToken.toBuilder().accessToken(renewToken).build();
            memberTokenRepository.save(memberToken);
            throw new JwtAuthException(CustomErrorCode.JWT_AUTH_CODE,renewToken);
        }

        //log.info("닉네임 정보 : {}",nickname);
        System.out.println(request.getMethod()+" : "+request.getServletPath());
        return joinPoint.proceed();
    }

    @Order(value = 1)
    @Around("@annotation(com.als.SMore.user.login.util.JwtRole)")
    public Object isCheckedRole(final ProceedingJoinPoint joinPoint) throws Throwable {
        // request 랑 response 를 먼저 생성
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        // 토큰을 받아오기
        String token = request.getHeader("authorization").substring(7);

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        JwtRole custom = methodSignature.getMethod().getAnnotation(JwtRole.class);

        // URI 에서 studyPk를 가져옴
        String studyPk = request.getServletPath().split("/")[custom.index()];

        // tokenProvider 에서 토큰에 studyPk에 관한 역할이 있는 지 판단함.
        if(!tokenProvider.isCheckedRole(token,studyPk)){
            // 인터셉터를 만들어서 연결을 끊어 버리자
            //setResponse(response,"study에대한 접근 권한이 없습니다", 403);
            throw new CustomException(CustomErrorCode.NOT_FOUND_STUDY_PK);
        }

        String role = tokenProvider.getRole(token, studyPk);
        if(!role.equals("admin")){
            throw new CustomException(CustomErrorCode.NOT_FOUND_ROLE);
        }

        return joinPoint.proceed();
    }

    @Pointcut("execution(* com.als.SMore.study.*.controller.*.*(..))")
    public void controllerPointcut(){}
}
