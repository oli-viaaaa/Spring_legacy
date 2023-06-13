package com.javalab.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.ibatis.mapping.DatabaseIdProvider;
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
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

/**
 * 목(Mock) 테스트
 * - 컨트롤러는 웹으로부터 요청을 받기 때문에 서블릿 컨테이너 즉 톰캣을
 *   구동시켜야 하기 때문에 무겁고 디버깅 시간이 오래걸림. 그래서 가상으로
 *   톰캣을 구동시키는 것과 같은 환경을 만들어서 훨씬 빠르게 테스트 할 수있음.
 * - 마치 웹에서 요청을 하는 것과 같은 환경을 만들어서 테스트함.
 * - root-context.xml + servlet-context.xml 모두 필요
 * @RunWIth(SpringJUnit4ClassRunner.class) : 테스트를 실행하기 위해 
 *   SpringJUnit4ClassRunner를 사용하도록 JUnit에 지시
 * @WebAppConfiguration : 톰캣 구동없이 테스트 할 수 있는 환경 사용할 수 
 *  있도록 설정. 테스트를 위해 웹 애플리케이션 컨텍스트를 로드하는 데 사용   
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration 
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Slf4j
public class MockMvcTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;	// 스프링 테스트에서 제공
	
	/*
	 * 테스트 하기 전에 실행되는 메소드
	 *  - 톰캣을 구동시키지 않고 컨트롤러를 테스트할 수 있는 환경 생성
	 */
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test @Ignore
	public void testContext() throws Exception{
		assertNotNull(mockMvc);
		log.info("mockMvc : " + mockMvc);		
	}
	
	/**
	 * 목테스트 방식 #1
	 * @throws Exception
	 */
	@Test //@Ignore 
	public void testList() throws Exception{
		log.info("목(MockMvc Test =========================");
		log.info(
				/* 가상으로 웹요청을 만듦 */
				mockMvc.perform(MockMvcRequestBuilders.get("/board/boardList.do"))
				.andReturn()
				.getModelAndView().getViewName()
				);
	}
	
	/*
	 * 목테스트 방식 #2
	 *  - 메소드 오류나면 add static import 해줄것. 
	 */
    @Test @Ignore
    public void testGetBoardList() throws Exception {
        // Perform the mock request and assertions
        this.mockMvc.perform(get("/board/boardList.do"))
				/* HTTP 응답 상태가 200 OK인지 확인 즉, 요청이 성공했는지 확인 */
                .andExpect(status().isOk())
				/* 반환된 뷰 네임 확인 */
                .andExpect(view().name("/board/boardList"))
				/* 모델(model)에 여기에 기재한 내용의 저장된 값이 있는지 확인 */
                .andExpect(model().attributeExists("boardList"));
    }
	
}
