package com.javalab.board.common;

/*
 * 어드바이스 클래스
 * 	- 서비스 Layer의 메소드 실행 후에 작동되는 로직을 갖고 있는 클래스 
 */

public class AfterAdvice {
	
	public AfterAdvice() {
	}
	
	public void finallyLog() {
		System.out.println("[AfterAdvice] 핵심(타겟, 포인트컷) 메소드 실행 후 동작합니다.");
	}
}
