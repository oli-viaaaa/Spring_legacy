package com.javalab.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.javalab.service.LoginService;
import com.javalab.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

	//@Autowired //만 해도 됨.
	//@Qualifier("loginService")
	@Resource(name = "loginService")
	private LoginService service;	// loginService라는 이름의 빈을 찾아서 주입해줌
	
	// 기본 생성자
	public LoginController() {
		super();
	}

	// 로그인폼을 띄워주는 메소드(핸들러)
	@GetMapping("/login.do")
	public String loginForm(UserVo userVo, Model model){
		log.info("getUser GET() 메소드 ");
		return "/login/loginForm";	// WEB-INF/views/login/loginForm.jsp 
	}
	
	@PostMapping("/login.do")
	public String login(@ModelAttribute("user") UserVo vo, 
						HttpSession session,			// 사용자 로그인 정보기록
						HttpServletResponse response,	// 쿠키 때문에 필요 
						Model model){
		
		log.info("login POST() 메소드");
		
		String returnUrl = ""; // 로그인 성공 유무에 따라서 다양한 리턴 타입 용도
		
		// 서비스단에 아이디/비밀번호 전달해서 조회함.
		UserVo userVo = service.getUserById(vo);
		
		// 로그인 정보가 맞음
		if(userVo != null) {
			log.info("로그인 성공! userVo : " + userVo.toString());
			
			// 기존 세션이 존재하면 제거하고 아래서 다시 새롭게 저장한다
			if (session.getAttribute("user") != null ){
				session.removeAttribute("user"); 
			}

			// 세션에 사용자 로그인 정보 기록
			session.setAttribute("user", userVo);
			session.setMaxInactiveInterval(3600);	// 세션 유지 시간 지정(1시간, 초단위)
			log.info("세션에 기록 성공");
			
			// 쿠키에도 기록(사용자 아이디로 쿠키 저장)
			Cookie cookie = new Cookie("id", userVo.getId());
			cookie.setMaxAge(10*60);	// 600초 : 10분
			// setPath : 모든 페이지에 쿠키를 전송하겠다는 의미, 패스 없으면 쿠키를 설정한 화면에만 쿠키 전송
			cookie.setPath("/");	
			response.addCookie(cookie);			
			log.info("사용자 웹브라우저에 쿠키 저장 성공 cookie값 : " + cookie.getValue());
			
			// 세션에서 원래 가려고 했던 url로 가져옴, 없으면 기본적으로 게시물 목록으로 이동
			// 가려고 했던 Url은 로그인 필터에서 넣어주었음
			// [필터] - 인터셉터 기능 구현 후에 주석 해제할것
			String previousUrl = (String)session.getAttribute("previousUrl");
			if(previousUrl != null) {
				returnUrl = "redirect:" + previousUrl; // 원래 가려고 했던 곳으로 이동
			}else {
				returnUrl = "redirect:" + "/board/boardList.do"; // 게시판 목록으로 이동
			}
			
		}else {	//아이디/비밀번호가 존재하지 않으면, loginErrMsg = true 세팅
			log.info("로그인 실패!");
			model.addAttribute("loginErrMsg", true); // 오류 메시지를 true로 세팅
			// 파라미터에서 @ModelAttribute("user") UserVo vo 했기 때문에 다음 코드 필요 없음
			//model.addAttribute("user", vo); 		 
			returnUrl = "/login/loginForm"; // 다시 로그인 폼으로 이동
		}
		log.info("리턴 Url : " + returnUrl);
		
		return returnUrl;	
	}
	
	// 로그아웃 메소드(핸들러)
	@GetMapping("/logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model){
		log.info("logout 메소드");
		
		// 1. 세션 무효화
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
			System.out.println("세션 무효화 완료!");
		}
		
		// 2. 쿠키 만료
		Cookie cookie = new Cookie("id", "");
		if(cookie != null) {
			cookie.setPath("/"); // 패스도 설정해야 완전히 삭제됨.
			cookie.setMaxAge(0); // 쿠키 만료
			response.addCookie(cookie);
			System.out.println("쿠키 삭제 완료!");			
		}
		
		return "/login/loginForm";	// loginForm.jsp
	}	
	
}
