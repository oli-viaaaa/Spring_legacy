package com.javalab.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.javalab.dao.LoginDao;
import com.javalab.service.LoginService;
import com.javalab.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"
      })
@Slf4j
public class HikariCPTest {
	@Inject
	private DataSource dataSource;

	@Inject
	private SqlSessionFactory sf;
	
	@Inject
	private LoginDao dao;
	
	@Inject
	private LoginService service;

	
	// HikariCP 커넥션풀 생성 여부 확인 Test
	@Test @Ignore
	public void testDataSource() {
		assertNotNull(dataSource);
		try(Connection conn = dataSource.getConnection()){
			log.info(conn + ""); // 커넥션 객체 주솟값 출력
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}
	// SqlSessionFactory 빈 생성여부 확인
	@Test @Ignore
	public void testSqlSessionFactory() {
		try(SqlSession session = sf.openSession();
			Connection conn = dataSource.getConnection()){
			log.info(session + ""); // 커넥션 객체 주솟값 출력
			log.info(conn + ""); // 커넥션 객체 주솟값 출력
			assertNotNull(conn);
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test @Ignore
	public void testLoginDao() {
		assertNotNull(dao);
		System.out.println(dao);
	}		
	
	@Test
	public void testGetUser() {
		// [1] 조회할 사용자 객체 생성
		UserVo vo = new UserVo();
		vo.setId("1");	// 데이터베이스에서 ID:1인 사용자 조회
		vo.setPwd("2");	// 데이터베이스의 비밀번호 확인후 코딩
		
		// [2] 위에서 만든 사용자 객체를 Dao 단에 전달해서 조회
		UserVo user = dao.getUserById(vo);
		assertEquals("1", user.getId());
		System.out.println(user.toString());
	}	

	@Test  @Ignore
	public void testGetUserService() {
		// [1] 조회할 사용자 객체 생성
		UserVo vo = new UserVo();
		vo.setId("1");	// 데이터베이스에서 ID:1인 사용자 조회
		vo.setPwd("2");	// 데이터베이스의 비밀번호 확인후 코딩
		
		// [2] 위에서 만든 사용자 객체를 Dao 단에 전달해서 조회
		UserVo user = service.getUserById(vo);
		assertEquals("1", user.getId());
		System.out.println(user.toString());
	}		
	
}
