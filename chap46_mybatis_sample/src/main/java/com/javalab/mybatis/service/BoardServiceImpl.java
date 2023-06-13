package com.javalab.mybatis.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalab.mybatis.dao.BoardMapperInterface;
import com.javalab.mybatis.vo.BoardVo;



/**
 * [비즈니스 로직, 서비스 단(Layer)]
 *  - BoardService 인터페이스를 구현한 구현체
 *  - 업무의 주된 핵심 로직이 구현되는 단(Layer).
 *  - 컨트롤러와 Dao 단을 연결하는 역할.
 *  - AOP가 적용되는 Leyer(단).
 *  - @Service 어노테이션으로 인해서 자동으로 빈으로 스캔되어 등록됨.
 */
@Service("boardService")
public class BoardServiceImpl implements BoardService {

	// 멤버 변수
	@Autowired
	private BoardMapperInterface boardDao;	// BoardDao 객체 자동주입

	@Override
	public ArrayList<BoardVo> selectBoardList() {
		ArrayList<BoardVo> boardList = boardDao.selectBoardList();
		return boardList;
	}

	@Override
	public int deleteBoard(int no) {
		int result = 0;
		result = boardDao.deleteBoard(no);
		return result;
	}

	@Override
	public BoardVo getBoardById(int no) {
		BoardVo boardVo = null;
		updateHitCount(no);	// 게시물 조회수 업데이트
		boardVo = boardDao.getBoardById(no);
		return boardVo;
	}

	@Override
	public int modifyBoard(BoardVo boardVo) {
		//logAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		int result = 0;
		result = boardDao.modifyBoard(boardVo);
		return result;
	}

	@Override
	public int insertBoard(BoardVo vo) throws Exception{
		//logAdvice.printLog(); // 메소드 마다 로그 남기기 코드 추가
		int result = 0;
		result = boardDao.insertBoard(vo);
		return result;
	}

	@Override
	public void updateHitCount(int no) {
		boardDao.updateHitCount(no);
	}



}
