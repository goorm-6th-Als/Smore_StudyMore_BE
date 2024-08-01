package com.als.SMore.user.login.util.aop;

import com.als.SMore.user.login.util.aop.dto.AopDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class BasicJwtAop {

    public String getStudyPk(HttpServletRequest request){
        int index = 2;

        // URI 에서 studyPk를 가져옴
        String studyPk = request.getServletPath().split("/")[index];
        log.info("스터디 pk : {}",studyPk);

        return studyPk;
    }
}
