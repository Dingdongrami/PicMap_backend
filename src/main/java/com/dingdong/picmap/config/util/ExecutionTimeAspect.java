package com.dingdong.picmap.config.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class ExecutionTimeAspect {

    @Around("@annotation(measureExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, MeasureExecutionTime measureExecutionTime) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        long end = System.currentTimeMillis();
        log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms" + " (start: " + start + ", end: " + end + ")");

        return proceed;
    }
}
