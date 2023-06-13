package com.javalab.mybatis.controller;

import java.util.ArrayList;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.javalab.mybatis.service.BoardService;
import com.javalab.mybatis.vo.BoardVo;


/**
 * 컨트롤러 클래스
 *  - 어플리케이션이 구동되면서 servlet-context.xml에 설정된 바와 같이
 *    @Controller 어노테이션이 붙은 클래스들을 빈으로 등록한다.
 *  - JSP의 요청을 받아서 요청 사항을 처리하고 처리 결과를 model에 저장한다.
 *  - 처리 메소드에서는 최종적으로 JSP 페이지 이름을 리턴하거나 다른 메소드를
 *    redirect 형태로 반환하기도 한다.     
 *
 */
@Controller
public class BoardController {

	/**
	 * 의존성주입
	 *  - 스프링 컨테이너에 빈으로 등록되어 있는 객체를 주입해줌
	 *  1) @Autowired : Type으로 찾아옴, 스프링 제공
	 *  2) @Inject : Type으로 찾아옴, 자바 제공
	 *  3) @Autowired + @Qualifier("이름")   
	 *  4) @Resource(name = "이름") : 자바 제공   
	 */
	
	// BoardServiceImpl --> BoardService(인터페이스)로 변경
	@Autowired
	private BoardService service;
	
	// 기본 생성자
	public BoardController() {
		System.out.println("BoardController 생성자");
	}
	
	/*
	 * 게시물 목록을 보여주는 메소드(핸들러)
	 * @RequestMapping :
	 *  - Url 요청이 왔을 때 어떤 컨트롤러가 호출이 되어야 하는지 알려주는 지표. 
	 *  - 어플리케이션이 구동되면서 핸들러 매핑은 어떤 url과 메소드가 연결되어 
	 *    있는지 정보를 모두 취합해서 Map 형태로 갖고 있다.url(Key - 메소드명(value) 
	 */
	
	/*
	 * Model model : 모델은 컨트롤러 단에서 저장한 데이터를 M-V-C중에서
	 * 	view 단에서 찾아 쓸 수 있도록 데이터를 보관하는 역할을 한다.
	 *  서블릿 프로젝트에서 request에 저장하던 방식과 유사하지만 request에
	 *  바로 저장되는 것은 아니다.
	 */
	@RequestMapping(value="/boardList.do", method = RequestMethod.GET)
	public String selectBoardList(Model model){
		ArrayList<BoardVo> boardList = service.selectBoardList();
		model.addAttribute("boardList", boardList);
		return "boardList";	// boardList.jsp
	}
	
	// 게시물 한개의 내용을 보여주는 메소드(핸들러)
	// @RequestParam : @RequestParam("받아올 데이터의 이름") [데이터타입] [가져온데이터를 담을 변수]
	@RequestMapping(value="/boardView.do", method = RequestMethod.GET)
	public String getBoardById(@RequestParam("no") int no, Model model){
		BoardVo boardVo = service.getBoardById(no);
		model.addAttribute("board", boardVo);
		return "boardView";	// boardView.jsp
	}
	
	// 게시물 작성 폼을 띄워주는 메소드(핸들러)
	@RequestMapping(value="/boardWrite.do", method = RequestMethod.GET)
	public String boardWriteForm(Model model){
		return "boardWrite";	// boardWrite.jsp
	}

	// 작성된 게시물을 데이터베이스에 저장하는 메소드(핸들러)
	@RequestMapping(value="/boardWrite.do", method = RequestMethod.POST)
	public String boardWrite(BoardVo vo, Model model) throws Exception{
		service.insertBoard(vo);
		return "redirect:boardList.do"; 
	}

	// 수정폼을 보여주는 메소드(핸들러)
	// @RequestParam("no") int no : jsp에서 ?no=1 과 같은 형태로 전달
	@RequestMapping(value="/boardModify.do", method = RequestMethod.GET)
	public String boardModifyForm(@RequestParam("no") int no, Model model){
		// 게시물 목록을 조회
		BoardVo boardVo = service.getBoardById(no);
		model.addAttribute("board", boardVo);
		return "boardModify";	// boardModify.jsp
	}
	
	// 수정한 내용을 데이터베이스에 반영하는 메소드(핸들러)
	@RequestMapping(value="/boardModify.do", method = RequestMethod.POST)
	public String boardModify(BoardVo vo, Model model){
		service.modifyBoard(vo);
		return "redirect:boardList.do"; 
	}

	// 게시물을 삭제해주는 메소드(핸들러)
	@RequestMapping(value="/boardDelete.do", method = RequestMethod.GET)
	public String boardModify(@RequestParam("no") int no){
		service.deleteBoard(no);
		return "redirect:boardList.do"; 
	}
}
