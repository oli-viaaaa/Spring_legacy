package com.javalab.board.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.javalab.board.service.BoardServiceImpl;
import com.javalab.board.vo.BoardVo;

/**
 * junit 단위테스트 클래스
 * 	- 테스트 클래스는 'BoardDao' 클래스와 동일한 패키지에 위치(src, test, java)
 * 
 * 	@Test : 테스트 케이스 작성, 각 메소드가 junit 테스트 클래스임을 명시
 * 	@Ignpre : 이 메소드는 테스에서 제외
 * 			  두 개를 같이 쓰면 테스트에서 제외됨(ex. @Test @Ignor)
 * 	@Befor : 테스트를 하기 전에 반드시 거쳐야 하는 부분을 기재
 * 	assertNotNull(검증객체) : 괄호 안에 있는 객체가 널이 아닐 것이라고 가정(assert)
 * 							널이 아니면 true 널이면 false가 됨.
 * 
 * 
 * 	@RunWith(SpringJunit4ClassRunner.claa) : 스프링에서 junit을 사용하기 위해서 스프링 컨테이너 대신
 * 											 application context 생성
 * 											 @Autowired 와 같은 어노테이션 사용할 수 있게 됨.
 * 	@ContextConfiguration("classpath:config/root-context.xml") : 단위 테스트에서 사용할 빈 등록 설정 파일 지정
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:config/root-context.xml")
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardDaoTest {
	
	// BoardMapperInterface 인터체이스 타입으로 의존성 주입
	@Autowired
	private BoardMapperInterface boardDao;
	
	
	// 핵심 빈(Bean)인 BoardDao가 정상적으로 의존성 주입되는지 확인
	@Test @Ignore // 같이 있는 경우 @Test가 무시된다
	public void testDao() {
		assertNotNull(boardDao);
	}
	
	
	// 게시물 목록을 조회하는 메소드 단위테스트
	@Test @Ignore
	public void testSelectBoardList() {
		ArrayList<BoardVo> boardList = boardDao.selectBoardList();
		assertNotNull(boardList); // 게시물 목록이 널인지 체크
		// 보고 싶을 때 아래와 같이 테스트 해 볼 수 있다.
		for(BoardVo boardVo : boardList) {
			System.out.println(boardVo.toString());
		}
	}
	
	// 게시물 한개 조회 메소드 테스트
	@Test @Ignore
	public void testGetBoardById() {
		int no = 1; //실제로 있는게시물 번로를 제공해준다.
		BoardVo board = boardDao.getBoardById(no);
		assertNotNull(board);
		assertEquals(no, board.getNo());
	}
	
	// 게시물 등록 메소드 테스트
	@Test @Ignore
	public void testInsertBoard() throws Exception {
		// 등록할 객체 생성
		BoardVo boardVo = new BoardVo();
		boardVo.setTitle("단위테스트로 등록된 게시물");
		boardVo.setContent("단위테스트로 등록된 게시물 내용");
		boardVo.setWriter("작성자");
		
		int result = boardDao.insertBoard(boardVo);
		assertEquals(1, result);
	}
	
	// 게시물 수정 메소드 단위 테스트
	@Test @Ignore
	public void testModifyBoard() {
		// 특정 게시물을 수정하기 위한 정보를 담을 객체 생성
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(1); //실제로 있는 게시물 번호
		boardVo.setTitle("수정된 게시물 제목");
		boardVo.setContent("수정된 게시물 내용");
		boardVo.setWriter("수정된 작성자");
	
		int result = boardDao.modifyBoard(boardVo);
		assertEquals(1, result);
	}

	// 삭제 메소드 단위테스트
	@Test //@Ignore
	public void testDeleteBoard() {
		int no = 2; // 실제 존재하는 게시물 번호
		int result = boardDao.deleteBoard(no);
		System.out.println("result " +result);
		assertEquals(1, result);
	}
}
