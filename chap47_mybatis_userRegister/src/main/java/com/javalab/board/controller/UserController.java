package com.javalab.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javalab.board.service.UserService;
import com.javalab.board.vo.RoleVo;
import com.javalab.board.vo.UserVo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")	// 컨트롤러 차원의 RequestMapping
@Slf4j
//@AllArgsConstructor			// 전체 멤버 변수가 들어가는 생성자를 만들어줌.
public class UserController {

	/*
	 * [생성자를 통한 의존성 주입]
	 *  - @AllArgsConstructor 통해서 멤버 변수 전체를 받는 생성자 생성됨.
	 *  - 어노테이션 없이도 멤버 변수들의 의존성이 자동으로 주입됨.
	 *  - 컨트롤러에서 다른 컨트롤러를 사용해야 할 경우도 있음. BoardService
	 *  - 기본 생성자는 없어야 됨.
	 */
	private UserService service;
	//private BoardService boardService;

	/*
	 * [생성자 의존성 주입]
	 *  - 생성자가 있으면 자동으로 의존성이 주입됨.
	 */
	public UserController(UserService service /* , BoardService boardService */) {
		super();
		this.service = service;
		//this.boardService = boardService;
	}
	
	/*
	 * 이 클래스에서 공통적으로 자주 사용하는 정보를 미리 세팅해놓음
	 *  - 메소드마다 코드 중복 해소 차원에서 사용. 옵션 사항임.
	 *  - 다른 메소드가 호출되면 이 메소드가 우선해소 호출됨.
	 *  - 처리 결과는 model에 저장됨.
	 */
	/*
	@ModelAttribute("userVo")
	public Map<String, Object> callFirstofAll() {

		log.info("callFirstofAll() 메소드");

		Map<String, Object> userVo = new HashMap<>();

		// 부서 정보 조회
		List<RoleVo> roleList = service.getRoles();
		
		log.info("roles.size() " + roleList.size());

		// role 정보를 Map 객체에 담아서 리턴하면 JSP에서 꺼내씀.
		userVo.put("roleList", roleList);

		return userVo;
	}	
	*/
	
	/*
	 * 회원 가입 폼을 띄워주는 메소드(핸들러) 
	 */
	@GetMapping("/join.do")
	public String joinForm(@ModelAttribute("user") UserVo user,   Model model){
		log.info("joinForm GET() 메소드 ");

		// 부서 정보 조회
		List<RoleVo> roleList = service.getRoles();
		
		log.info("roles.size() " + roleList.size());

		model.addAttribute("roleList", roleList);
		
		return "/user/joinForm"; 
	}	
	
	/*
	 * 회원 아이디 중복 체크
	 * @PathVariable 형태로 전달받을 때 메소드
	 */
	/*
	@ResponseBody
	@GetMapping("/idCheck.do/{id}")
	public int idCheck(@PathVariable("id") String id, Model model){
		log.info("idCheck 메소드");
		int isIdExist = 0;	// 기본은 중복
		isIdExist = service.idCheck(id);	//중복 여부 체크
		return isIdExist;
	}
	*/
	
	/*
	 * 회원 아이디 중복 체크
	 * @RequestParam 형태로 전달받을 때 메소드
	 */
	@ResponseBody
	@GetMapping("/idCheck.do")
	public int idCheck(@RequestParam("id") String id, Model model){
		log.info("idCheck 메소드");
		int isIdExist = 0;	// 기본은 중복
		isIdExist = service.idCheck(id);	//중복 여부 체크
		return isIdExist;
	}

	/*
	 * 회원 등록 메소드(핸들러) 
	 */
	@PostMapping("/join.do")
	public String insertUser(UserVo vo, Model model) {  
		service.insertUser(vo);	// 회원 등록
		model.addAttribute("joinId", vo.getId()); 
		// 로그인 페이지로 이동(로그인 컨트롤러)해서 아이디 자동 세팅하기 위해서
		// 쿼리스트링으로 달려서 보냄.
		return "redirect:/login/login.do?" + "id=" + vo.getId(); 
	}
	
	/*
	 * 회원 조회 - 다양한 조건으로
	 */
	@GetMapping("/userList.do")
	public String getUserListByCon(UserVo user, Model model) {
		List<UserVo> userList = service.getUserByCon(user);
		model.addAttribute("userList",userList);
		return "userList"; // userList.jsp
	}
	
}
