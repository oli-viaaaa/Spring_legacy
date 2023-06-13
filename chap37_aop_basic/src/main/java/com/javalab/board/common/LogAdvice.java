package com.javalab.board.common;

/*
 * [어드바이스]
 * 	- 공통로직으로 특정 메소드가 호출 될 때마다 그 메소드 실행 전에
 * 	  이 클래스의 printLog() 메소드를 먼저 호출해서 로그를 남기도록 하낟.
 * 	  이런 클래스를 어드바이스 클래스라고 한다.
 */

public class LogAdvice {
	
	public LogAdvice() {
	}
	
	public void printLog() {
		System.out.println("[로그기록] 비지니스 로직 수행전 로그를 남깁니다.");
	}
}
