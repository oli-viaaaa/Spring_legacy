package com.javalab.controller;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.javalab.dao.BoardDAO;
import com.javalab.service.BoardService;
import com.javalab.vo.BoardVO;
import com.javalab.vo.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Log4j
public class BoardPagingTest {

	@Inject 
	private BoardDAO dao;
	
	@Inject
	private BoardService service;
	
	@Inject
	private HomeController homeController;
	
	private MockMvc mockMvc;
	
	@Before	// import org.junit.Before;
	public void createController() {
		mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
	}
	
	// Dao 단 테스트
	@Test @Ignore
	public void testDaoPaging() {
		Criteria cri = new Criteria(1, 10);
		List<BoardVO> list = dao.getListPagingAndSearching(cri);
		list.forEach(board -> log.info(board.toString()));
	}
	
	// Service 단 테스트
	@Test @Ignore
	public void testPagingService() {
		Criteria cri = new Criteria(1, 10);
		List<BoardVO> list = service.getListPagingAndSearching(cri);
		list.forEach(board -> {log.info(board.toString());});
	}
	
	// Controller 단 테스트
	/*
	 * [통합테스트]
	 *  - 스프링 MVC의 프레임워크 기능까지 결합된 상태인 통합 테스트 해줌.
	 *  - MockMvc는 웹 애플리케이션을 애플리케이션 서버에 배포하지 않고도 
	 *    스프링 MVC의 동작을 재현.
	 */
//	@Test
//	public void testPagingController() {
////		Criteria cri = new Criteria(1, 10);
////		List<BoardVO> list = service.getListPaging(cri);
////		list.forEach(board -> {log.info(board.toString());});
//		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/boardList.do")
//				.param("pageNum", "2")
//				.param("amount", "20")) 
//				.andReturn().getModelAndView().getModelMap()
//				);
//		
//	}
	
	@Test @Ignore
	public void testRegister() throws Exception {
		String resultPage = mockMvc
				.perform(MockMvcRequestBuilders.post("/board/boardWrite.do")
				.param("title", "테스트 새글 제목")
				.param("content", "테스트 새글 내용")
				.param("id", "user00"))
				.andReturn().getModelAndView().getViewName();
		log.info(resultPage);

	}

	@Test  @Ignore
	public void testListPaging() throws Exception {
		log.info(mockMvc.perform(
				MockMvcRequestBuilders.get("/board/boardList.do")
				.param("pageNum", "2")
				.param("amount", "10"))
				.andReturn().getModelAndView().getModelMap());
	}	
	
}
