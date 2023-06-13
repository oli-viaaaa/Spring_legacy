package com.javalab.board.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javalab.board.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

/**
 * [컨트롤러]
 * 	- 스프링 
 */
@Controller
@Slf4j
public class HomeController {
	
	//@GetMapping 어노테이션 : Spring Framework 버전 4.3에서 도입
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	// formRest 페이지 오픈
	@PostMapping("/formRest")
	public String formRest(@ModelAttribute("member") MemberVo member) {
		return "formRest"; // 응답 페이지 : formRest.jsp
	}

	
	
	
	// 응답 페이지 : formRest2.jsp
	// 응답 페이지가 생략되면 호출된 url formRest2가 반환된어 formRest2.jsp 페이지를 찾아간다.
	@RequestMapping(value="/formRest2", method = {RequestMethod.POST, RequestMethod.GET})
	public void formRest2(@ModelAttribute("name")String name) {
		// return "formRest2" // 생략해도 응답페이지 :formRest2.jsp
	}
	
	/*
	 * formRest.jsp 에서 ajax형태로 호출됨.
	 * [@PathVariavle] 형태로 값을 전달 받는 방법
	 * 	- 경로명?name="홍길동"&grade="3" 와 같은 형태
	 * [@ResponseBody]
	 * 	-호출한 곳으로 응답할때 "메시지 바디"에 담아서 전달해주면 받는 jsp 페이지 body에 그대로 전달됨.
	 * 	 자바 스크립트에서 그걸 가공해서 사용함 
	 * - <jackson-databind>디펜던시(라이브러리)가 필요함.
	 *   Jackson은 JAVA애플리케이션에서 JSON데이터를 처리하는데 널리 사용되며 JSON 데이터를 JAVA 객체로 변환(역직렬화) 하고
	 *   JAVA 객체를 JSON 데이터로 변환(직렬화)하는 기능을 제공함
	 * - Map<String, Object> : Map 타입으로 key-value 형태를 JSON Type문자열로 반환
	 */
	
	@RequestMapping(value = "/action3.do/name1/{name2}/grade1/{grade2}", 
					method = {RequestMethod.GET, RequestMethod.POST})
	
	public @ResponseBody Map<String, Object> action3(
			@PathVariable("name2")String name, 
			@PathVariable("grade2")int grade, Model model){
		
		Map<String, Object> member = new HashMap<>();
		member.put("name", name);
		member.put("grade", grade);
		
		// 호출한 화면의 바디(Body)에 JSON 타입으로 전송되고
		// success callback 에서 자바스크립트를 통해서 화면의 특정 부분을살짝 변화시킴
		// @ResponseBody 어노테이션이 있어서 가능함.
		
		return member;
	}
	
	/*
	 *  
	 */
}
