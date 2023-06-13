package com.javalab.board;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 사용자의 요청을 처리하는 컨트롤러 클래스
 * 	- 서블릿의 doGet()/doPost 메소드가 처리하던 일을 컨트롤러가 처리함.
 * 	- @Controller 객체로 생성해서 스프링 컨테이너에 빈으로 등록한다.
 */
@Controller
public class HomeController {
	
	/*
	 * 스프링 프로젝트 생성시 pom.xml에 이미 의존성이 들어와 있음.
	 * 의존성 : slf4j-api
	 * 	- 여기에 가보면 org.slf4j 패키지에 Logger 인터페이스가 있음
	 * 	- 여기에 LoggerFactory 클래스도 있음. 
	 */
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * 앱 실행시 최초로 요청되는 "/" 요청을 처리하는 메소드
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		// 1. System.out.println() 대신에 Logger.info()사용
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		// 2. System.out.println() 대신에 Logger.info()사용
		logger.info("info formattedDate " + formattedDate);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}
