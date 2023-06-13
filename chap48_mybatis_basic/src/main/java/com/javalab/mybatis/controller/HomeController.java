package com.javalab.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	public HomeController() {
	}
	
	 @GetMapping("/")
	 public String home() {
		return "redirect:/emp/list";
	 }
}
