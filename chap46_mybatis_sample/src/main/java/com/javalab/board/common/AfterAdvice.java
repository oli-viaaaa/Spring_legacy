package com.javalab.board.common;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

/**
 * 어드바이스 클래스
 * 	- 서비스 Layer의 메소드 실행 후에 작동하는 로직을 갖고 있는 클래스
 */
@Service
@Aspect
public class AfterAdvice {

	public AfterAdvice() {
		System.out.println("AfterAdvice 어드바이스 객체 생성");
	}

	@Pointcut("execution(* com.javalab.board..*Impl.*(..))")
	public void allPointCut(){}
	
	@Pointcut("execution(* com.javalab.board..*Impl.get*(..))")
	public void getPointCut(){}	
	
	@After("allPointCut()")
	public void finallyLog(){
		System.out.println("[AfterAdvice - 사후처리] 타겟 메소드 실행 후에 수행됩니다.");
	}

}
