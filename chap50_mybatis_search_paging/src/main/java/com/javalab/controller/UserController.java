package com.javalab.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.javalab.service.BoardService;
import com.javalab.service.LoginService;
import com.javalab.service.UserService;
import com.javalab.vo.BoardVO;
import com.javalab.vo.Criteria;
import com.javalab.vo.RoleVo;
import com.javalab.vo.UserVo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")	// 컨트롤러 차원의 RequestMapping
@Slf4j
@AllArgsConstructor			//
public class UserController {

	/**
	 * [생성자를 통한 의존성 주입]
	 *  - @AllArgsConstructor 통해서 멤버 변수 전체를 받는 생성자 생성됨.
	 *  - 어노테이션 없이도 멤버 변수들의 의존성이 자동으로 주입됨.
	 *  - 컨트롤러에서 다른 컨트롤러를 사용해야 할 경우도 있음. LoginService, BoardService
	 *  - 기본 생성자는 없어야 됨.
	 */
	private UserService service;
	private LoginService loginService;
	private BoardService boardService;

	// @AllArgsConstructor를 사용하면 다음과 같은 생성자가 안보이게 만들어지면서
	// 의존성이 주입됨.
	/*
	public UserController(UserService service, LoginService loginService, BoardService boardService) {
		super();
		this.service = service;
		this.loginService = loginService;
		this.boardService = boardService;
	}
	*/	
	
	// 회원 가입 폼을 띄워주는 메소드(핸들러)
	@GetMapping("/join.do")
	public String joinForm(Model model){
		log.info("joinForm GET() 메소드 ");
		// 권한 리스트(jsp 화면의 관리자 select option에 세팅용)
		List<RoleVo> roleList = service.getRoles();
		model.addAttribute("roleList", roleList);	
		
		//int boardCount = boardService.getTotalBoardCount(Criteria cri);
		//log.info("총 게시물 건수 : " + boardCount);
		
		return "/user/joinForm"; 
	}	
	
	// 회원 아이디 존재 여부 체크
	@ResponseBody
	@GetMapping("/idCheck.do")
	public int idCheck(UserVo vo, Model model){
		log.info("idCheck 메소드");
		int isIdExist = 0;	// 기본은 중복
		isIdExist = service.idCheck(vo.getId());	//중복 여부 체크
		return isIdExist;
	}

	// 회원 등록 메소드(핸들러)
	@PostMapping("/join.do")
	public String insertUser(UserVo vo, Model model) throws IOException{  // 수정
		log.info("insertUser post 메소드 vo.toString() " + vo.toString());
		service.insertBoard(vo);	// 회원 등록(저장)
		return "redirect:/board/boardList.do"; // 게시물 목록 페이지 컨트롤러 호출
	}




}
