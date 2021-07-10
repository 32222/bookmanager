package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
	@AfterReturning(
			pointcut = "within(com.example.demo.controller.*Controller)",
			returning = "result"
			)
	public void afterReturning(JoinPoint jp, Object result) {
		log.info("{}:Return結果 = {}", jp.getSignature(), result);
	}
	
	@AfterThrowing(
			pointcut = "bean(homeController)",
			throwing = "e"
			)
	public void afterThrowing(JoinPoint jp, Throwable e) {
		log.error("{}:処理中に例外が発生しました。:{}", jp.getSignature(), e.getMessage());
	}
}
