package com.javalab.board.common;

import org.aspectj.lang.ProceedingJoinPoint;

/*
 * 어라운드 어드바이스
 * 	- 타겟 메소드 실행 전 실행후 모두 동작하는 어드바이스 클래스이다.
 * 	- proceedingJoinPoint 객체를 메소드의 매개변수로 받아서 타겟 메소드를 알아낼 수 있으며
 * 	관련된 정보도 알아낼 수 있다.
 * 	이게 없으면 타겟 메소드를 호출할 수 없다. 
 */

public class AroundAdvice {

	public AroundAdvice() {
	}
	
	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("[AroundAdvice - Befor] : 타겟 메소드 수행 전에 실행됩니다.");
		
		// 인자로 주어진 proceddingJoinPoint 통해서 핵심 비즈니스 메소드 호출 가능
		// 다음 코드를 실행하지 않으면 타겟 메소드(타겟 메소드) 호출 불가.
		Object returnObj = pjp.proceed();
		
		System.out.println("[AroundAdvice - After] : 타겟 메소드 수행 후에 실행됩니다.");
		return returnObj; 
	}
}
