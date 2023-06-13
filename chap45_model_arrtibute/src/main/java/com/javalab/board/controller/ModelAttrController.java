package com.javalab.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.javalab.board.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

@Controller
@SessionAttributes("member")
@Slf4j // 롬복에서 제공해주는 로깅 유틸사용(스프링거 안씀)
public class ModelAttrController {
	/*
	 * [@ModelAttribute]
	 * 
	 * @ModelAttribute 어노테이션이 붙어있는 메소드가 있으면
	 * 
	 * @RequestMapping이 붙은 어떤 메소드보다 우선해서 실행되고 난 후에 실제로 메소드가 실행된다.
	 * 
	 * @ModelAttribute를 사용한 Method에서 객체를 return하면 Model에 자동적으로 담아준다.
	 * 
	 * @ModelAttribute("modleVo") : Jsp에서 "modelVo"라는 이름으로 사용 - ${modelVo.bloodType}
	 * : Jsp애서 꺼내쓸때 - ${modelVo.gender} : Jsp애서 꺼내쓸때 - 화면에 공통적으로 나오는 부분 세팅할 때 사용한다.
	 */

	@ModelAttribute("modelVo")
	public Map<String, Object> callFirstofAll() {
		log.info("가장 먼저 불려지는 callFirstofAll() 메소드");

		Map<String, Object> modelVo = new HashMap<>();

		// 혈액형 select:option 태그 출력 위한 코드
		List<String> bloodType = new ArrayList<String>();
		bloodType.add("A");
		bloodType.add("B");
		bloodType.add("O");
		bloodType.add("AB");

		modelVo.put("bloodType", bloodType);

		// 성별 select:option 태그 출력을 위한 코드
		List<String> gender = new ArrayList<String>();
		gender.add("man");
		gender.add("woman");

		modelVo.put("gender", gender);

		return modelVo;
	}

	@GetMapping("/form")
	public String modelForm(Model model) {
		log.info("modelForm 메소드");
		// 이동해갈 jsp 페이지의 <form:input>태그에 바인딩할 객체 생성
		MemberVo member = new MemberVo();
		member.setId("dragon");
		member.setName("홍길동");
		member.setGender("man");
		member.setBloodType("O");
		member.setAge(27);

		// jsp에 "member2"라는 이름으로 값들을 꺼내서
		// input text/radio/checkbok 등에 값세팅
		model.addAttribute("member2", member);

		return "form";
	}
}
