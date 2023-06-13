package com.javalab.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.javalab.board.dao.LoginDao;
import com.javalab.board.dao.UserDao;
import com.javalab.board.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Slf4j
public class UserMapperTest {
   /*
    * 의존성 주입 @Autowired와 동일한 기능
    * 매퍼 인터페이스 타입으로 의존성 주입
    */
	
	@Inject
	private UserDao dao;
	
	/*
	 * UserDao 객체 생성 여부 검증
	 * 	- 인터페이스이지만 주입된 객체는 MapperProxy@4938 객체임.
	 * 	- 매퍼 인터페이스와 매퍼 XML을 연결하는 대리 객체 성격.
	 */
	
	@Test @Ignore
	public void testUserDao() {
		assertNotNull(dao);
		log.info("dao : " + dao.toString());
	}
	
	// 게시물 검색
	//  - 테이블에 "java", "1", "홍길동", "a@a.com", "admin" 사용
   @Test //@Ignore
   public void testGetUserByCon() {
	   // [1] 검색 조건 게시물 객체
	   UserVo vo = new UserVo();
	   vo.setId("java");
	   vo.setName("홍길동");
	   vo.setEmail("a@a.com");
	   
	   // [2] 게시물 조회
	   List<UserVo> userList = dao.getUserByCon(vo);
	   assertNotNull(userList);
	   for(UserVo user : userList) {
		   log.info(user.toString());
	   }
   }
   


   
}
