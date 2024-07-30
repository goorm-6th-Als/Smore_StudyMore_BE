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
import jakarta.servlet.http.HttpSession;
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

import java.util.HashMap;
import java.util.Map;


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

        String studyPk = super.getStudyPk(request);

        // 세션에 스터디에 관한 정보가 없는 경우, 검사
        HttpSession session = request.getSession();

        // tokenProvider 에서 토큰에 studyPk에 관한 역할이 있는 지 판단함.
        if(session.getAttribute(String.valueOf(memberPk)) == null){
            //setResponse(response,"study에대한 접근 권한이 없습니다", 403);
            Map<String, String> roles = getRole(studyPk, memberPk);
            session.setAttribute(String.valueOf(memberPk),roles);
        }

        Map<String, String> roles = (Map<String, String>) session.getAttribute(String.valueOf(memberPk));

        if(session.getAttribute(String.valueOf(memberPk)) != null && roles == null){
            Map<String, String> roleList = getRole(studyPk, memberPk);
            session.setAttribute(String.valueOf(memberPk),roleList);
        }

        //log.info("닉네임 정보 : {}",nickname);
        System.out.println(request.getMethod()+" : "+request.getServletPath());
        return joinPoint.proceed();
    }

    @Pointcut("execution(* com.als.SMore.study.*.controller.*.*(..))")
    public void controllerPointcut(){}

    protected Map<String, String> getRole(String studyPk,Long memberPk){
        StudyMember studyMember = studyMemberRepository.findByStudyStudyPkAndMemberMemberPk(
                Long.parseLong(studyPk), memberPk
        ).orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_STUDY_PK));

        // studypk와 role 을 가지고 와야 한다.
        String role = studyMember.getRole();
        Map<String, String> roles = new HashMap<>() {
            {
                put(studyPk,role);
            }
        };
        return roles;
    }
}
