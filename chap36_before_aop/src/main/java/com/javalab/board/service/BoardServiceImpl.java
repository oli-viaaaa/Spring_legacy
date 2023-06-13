package com.javalab.board.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalab.board.common.AfterLogAdvice;
import com.javalab.board.common.LogAdvice;
import com.javalab.board.dao.BoardDao;
import com.javalab.board.vo.BoardVo;

/**
 * [비즈니스 로직, 서비스 단(Layer)] 
 * 	- BoardService 인터페이스를 구현한 구현체 클래스로 인터페이스의 메소드가
 * 	  호출되지만 실질적으로는 Impl 객체의 메소드가 대신 호출된다.
 * 	- 업무의 주된 핵심 로직이 구현되는 단(Layer). 
 * 	- 컨트롤러와 Dao 단을 연결하는 역할. 
 * 	- AOP가 적용되는 Leyer(단). 
 * 	- @Service 어노테이션으로 인해서 자동으로 빈으로 스캔(로딩, 생성)됨.
 */
@Service("boardService")
public class BoardServiceImpl implements BoardService{

	// 멤버 변수로 Dao 단의 빈이 자동 주입됨(DI)
	@Autowired
	private BoardDao boardDao; // BoardDao 객체 자동주입
	
	// 공통로직 : 로그 남기는 역할의 클래스 // 추가 
	private LogAdvice logAdvice;
	private AfterLogAdvice afterLogAdvice;
	
	public BoardServiceImpl() {
		logAdvice = new LogAdvice(); // 추가
		afterLogAdvice = new AfterLogAdvice();
	}
	
	// 게시물 목록 조회
	@Override
	public ArrayList<BoardVo> selectBoardList() {
		// [부가로직 - 횡단 관심사(어드바이스)]
		logAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		
		// [핵심로직 - 핵심 관심사(포인트 컷)]
		ArrayList<BoardVo> boardList = boardDao.selectBoardList();
		
		// [부가로직 - 횡단 관심사(어드바이스)]
		afterLogAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		return boardList;
	}

	// 게시물 삭제
	@Override
	public int deleteBoard(int no) {
		logAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		int result = 0;
		result = boardDao.deleteBoard(no);
		afterLogAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		return result;
	}

	// 게시물 한개 조회
	@Override
	public BoardVo getBoardById(int no) {
		logAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		BoardVo boardVo = null;
		updateHitCount(no); // 게시물 조회수 업데이트
		boardVo = boardDao.getBoardById(no);
		afterLogAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		return boardVo;
	}

	// 게시물 수정
	@Override
	public int modifyBoard(BoardVo boardVo) {
		logAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		int result = 0;
		result = boardDao.modifyBoard(boardVo);
		afterLogAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		return result;
	}

	// 게시물 등록
	@Override
	public int insertBoard(BoardVo vo) {
		logAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		int result = 0;
		result = boardDao.insertBoard(vo);
		afterLogAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		return result;
	}

	// 게시물 조회수 증가
	@Override
	public void updateHitCount(int no) {
		logAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		boardDao.updateHitCount(no);
		afterLogAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
	}

}