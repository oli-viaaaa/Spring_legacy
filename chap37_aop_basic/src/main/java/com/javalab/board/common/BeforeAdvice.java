package com.javalab.board.common;

public class BeforeAdvice {
	
	public BeforeAdvice() {
		System.out.println("BeforAdvice 어드바이스 객체 생성됨.");
	}
	
	public void beforeLog() {
		System.out.println("[BeforAdvice] 핵심 메소드 실행전에 로그를 남깁니다.");
	}
}
