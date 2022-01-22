package com.hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


// AOP는 공통관심사항으로 수동 Bean으로 등록하는 것이 좋음.
@Aspect
public class TimeTraceAop {

    @Around("execution(* com.hello.hellospring..*(..)) && !target(com.hello.hellospring.SpringConfig)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{

        long start = System.currentTimeMillis();

        System.out.println("START: " + joinPoint.toLongString());

        try{
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("END: " + joinPoint.toString()+ " " + timeMs +"ms");
        }

    }
}
