package com.als.SMore.log.timeTrace;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@Aspect
public class TimeTraceAspect {
    @Pointcut("@annotation(com.als.SMore.log.timeTrace.TimeTrace)")
    //@annotation 특정 어노테이션이 적용된 메서드에 대해 포인트컷을 정의
    public void timeTraceMethod() {}

    @Pointcut("@within(com.als.SMore.log.timeTrace.TimeTrace)")
    // within 특정 어노테이션이 적용된 클래스 내의 모든 메서드에 대해 포인트컷을 정의
    public void timeTraceClass() {}

    @Around("timeTraceMethod() || timeTraceClass()")
    public Object traceTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        try {
            stopWatch.start();
            log.info("started method : {} ", joinPoint.getSignature().toShortString());
            return joinPoint.proceed(); // 실제 타겟 호출
        } finally {
            stopWatch.stop();
            log.info("{} - Total time = {}s",
                    joinPoint.getSignature().toShortString(),
                    stopWatch.getTotalTimeSeconds());
        }
    }
}
