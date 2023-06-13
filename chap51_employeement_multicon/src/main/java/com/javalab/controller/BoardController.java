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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javalab.service.BoardService;
import com.javalab.vo.BoardVO;
import com.javalab.vo.Criteria;
import com.javalab.vo.PageDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")	// 컨트롤러 차원의 RequestMapping
@Slf4j
@AllArgsConstructor
public class BoardController {

	/*
	 * [생성자를 통한 의존성 주입 방법 #1]
	 * 1. @AllArgsConstructor + private BoardService boardService;
	 */
	private BoardService boardService;

	// 페이징 기능 있는 게시물 리스트 조회 핸들러
	@GetMapping("/boardList.do")
	public String getListPaging(Criteria cri, Model model){
		log.info("selectBoardList 메소드 Criteria : " + cri);
		
		List<BoardVO> boardList = boardService.getListPagingAndSearching(cri);
		model.addAttribute("boardList", boardList);
		
		int total = boardService.getTotalBoardCount(cri);
		PageDto dto = new PageDto(cri, total);
		
		log.info("dto : " + dto.toString());
		model.addAttribute("pageMaker", dto); 	
		
		return "/board/boardList";	// jsp 페이지
	}
	
	// 게시물 한개의 내용을 보여주는 메소드(핸들러)
	@GetMapping("/boardView.do")
	public String getBoardById(@RequestParam("no") int no, 
								@ModelAttribute("cri") Criteria cri, 
								Model model){
		log.info("getBoardById 메소드 cri : " + cri.toString());
		BoardVO vo = new BoardVO();
		vo.setNo(no);
		BoardVO boardVo = boardService.getBoardById(vo);
		model.addAttribute("board", boardVo);
		return "/board/boardView";	// boardView.jsp
	}
	
	// 게시물 작성 폼을 띄워주는 메소드(핸들러)
	@GetMapping("/boardWrite.do")
	public String boardWriteForm(Model model){
		log.info("boardWriteForm get 메소드");
		return "/board/boardWriteForm";	// boardWriteForm.jsp
	}

	// 작성된 게시물을 데이터베이스에 저장하는 메소드(핸들러)
	@PostMapping("/boardWrite.do")
	public String insertBoard(BoardVO vo, 
							Model model,
							@ModelAttribute("cri") Criteria cri,
							RedirectAttributes rttr) throws IOException{  // 수정
		log.info("boardWriteForm post 메소드");
		
		// 파일 업데이트
		/*
		MultipartFile uploadFile = vo.getUploadFile();
		if(!uploadFile.isEmpty()){
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File("C:/filetest/upload/" + fileName));
		}
		*/
		
		// 게시물 등록(저장)
		boardService.insertBoard(vo);
		
		// redirect 하기 때문에 다음과 같이 넣어줘야 함.
		// rttr.addAttribute는 GET 방식이며 페이지를 새로고침 한다 해도 값이 유지된다.
		// rttr.addFlashAttribute는 POST 방식이며 이름처럼 일회성 데이터라 새로고침 하면 값이 사라진다.
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("searchText", cri.getSearchText());
		
		// 저장후 목록 출력 컨트롤러 호출, redirect하면 사용자 화면의 주소창이 변경됨.
		return "redirect:/board/boardList.do"; 
	}

	// 수정폼을 보여주는 메소드(핸들러)
	@GetMapping("/boardModify.do")
	public String updateBoardForm(BoardVO vo, 
									@ModelAttribute("cri") Criteria cri,
									Model model){
		
		log.info("updateBoardForm get 메소드 cri : " + cri.toString());

		BoardVO boardVo = boardService.getBoardById(vo);
		model.addAttribute("board", boardVo);
		return "/board/boardModifyForm";	// boardModify.jsp
	}
	
	// 수정한 내용을 데이터베이스에 반영하는 메소드(핸들러)
	@PostMapping("/boardModify.do")
	public String boardModify(BoardVO vo, 
								@ModelAttribute("cri") Criteria cri,
								RedirectAttributes rttr){
		
		log.info("boardModifyForm post 메소드 vo : " + vo.toString());
		boardService.updateBoard(vo); // 수정
		
		// redirect 하기 때문에 다음과 같이 넣어줘야 함.
		// rttr.addAttribute는 GET 방식이며 페이지를 새로고침 한다 해도 값이 유지된다.
		// rttr.addFlashAttribute는 POST 방식이며 이름처럼 일회성 데이터라 새로고침 하면 값이 사라진다.
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("searchText", cri.getSearchText());
		
		return "redirect:/board/boardList.do"; // 저장후 목록 출력 컨트롤러 호출
	}

	// 게시물을 삭제해주는 메소드(핸들러)
	@RequestMapping(value="/boardDelete.do", method = RequestMethod.GET)
	public String boardDelete(BoardVO vo, 
								@ModelAttribute("cri") Criteria cri,
								RedirectAttributes rttr){
		log.info("boardDelete 메소드 cri : " + cri.toString());
		boardService.deleteBoard(vo);
		
		// redirect 하기 때문에 다음과 같이 넣어줘야 함.
		// rttr.addAttribute는 GET 방식이며 페이지를 새로고침 한다 해도 값이 유지된다.
		// rttr.addFlashAttribute는 POST 방식이며 이름처럼 일회성 데이터라 새로고침 하면 값이 사라진다.
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("searchText", cri.getSearchText());
	
		return "redirect:/board/boardList.do"; // 저장후 목록 출력 컨트롤러 호출
	}
	
	// 전체 게시물 또는 조회 조건에 맞는 전체 게시물 건수
	@GetMapping("/boardCount.do")
	public int getTotalBoardCount(Criteria cri, Model model){
		log.info("getTotalBoardCount 메소드 cri : " + cri.toString());
		int count = boardService.getTotalBoardCount(cri);
		return count;
	}
}
