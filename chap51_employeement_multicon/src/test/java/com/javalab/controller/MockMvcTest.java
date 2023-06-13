package com.javalab.controller;

import static org.junit.Assert.assertNotNull;

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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
//@Slf4j	// 안됨
@Log4j
//@AllArgsConstructor
public class MockMvcTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;	// 스프링 테스트에서 제공
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test @Ignore
	public void testContext() throws Exception{
		assertNotNull(mockMvc);
		log.info("mockMvc : " + mockMvc);		
	}
	
	@Test 
	public void testList() throws Exception{
		System.out.println("testList <<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println(
				mockMvc.perform(MockMvcRequestBuilders.get("/board/boardList.do"))
				.andReturn()
				.getModelAndView().getViewName()
				//.getModelMap()
				);
	}
	
}
