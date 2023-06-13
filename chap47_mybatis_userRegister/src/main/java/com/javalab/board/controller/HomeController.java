package com.javalab.board.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@GetMapping("/")
	public String home(Locale locale, Model model) {
		log.info("Welcome home!");
		//return "redirect:/board/boardList.do";
		//return "redirect:/login/login.do";
		return "redirect:/user/join.do";	// 회원 가입폼 띄움
	}
}
