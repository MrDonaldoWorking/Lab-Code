package com.donaldo.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class StatisticsAspect {
    private final Map<String, Integer> executions = new HashMap<>();
    private final Map<String, Long> elapses = new HashMap<>();

    @Around("execution(* com.donaldo.aop.controller.ApplicationController.*(..))")
    public Object advice(final ProceedingJoinPoint joinPoint) throws Throwable {
        final String methodName = joinPoint.getSignature().toString();
        executions.putIfAbsent(methodName, 0);
        executions.put(methodName, executions.get(methodName) + 1);

        final long start = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        final long end = System.currentTimeMillis();
        elapses.putIfAbsent(methodName, 0L);
        elapses.put(methodName, elapses.get(methodName) + end - start);

        System.out.printf("%s:%n\tcalled\t%d time(s)%n\ttotal\t%f ms elapsed%n\tmean\t%f elapsed%n",
                methodName,
                executions.get(methodName),
                elapses.get(methodName) / 1000.0,
                (double) elapses.get(methodName) / 1000.0 / executions.get(methodName)
        );

        return result;
    }
}
