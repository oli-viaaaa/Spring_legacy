package com.javalab.board.controller;

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
	
	// 로그 출력 클래스(기본 제공)
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	/**
	 * 메소드
	 *  - ("/") : 웹 어플리케이션이 구동되고 맨 처음 요청을 처리하는 메소드
	 *  - 메소드는 처리한 후 보여줄 화면(View)의 이름을 반환한다.
	 *  - 이 메소드는 내용을 처리하고 home.jsp를 호출해서 사용자에게
	 *    결과를 출력하게 됨.  
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "redirect:boardList.do";
	}
	
}
