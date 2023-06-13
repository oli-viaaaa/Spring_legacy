package com.javalab.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.javalab.dao.BoardDAO;
import com.javalab.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;
/**
 * [트랜잭션이 적용된 클래스]
 *  - 클래스에 @Transactional 적용하면 클래스 전체에 트랜잭션이 적용된다. 
 *  - 메소드에 별도로 트랜잭션을 안걸어도 모든 메소드에 트랜잭션이 적용된다.
 *    즉, 모든 메소드를 실행하다가 (언체크드)예외 발생시 자동 롤백된다.
 *  - 만약 메소드에 별도로 트랜잭션이 적용되어 있으면 그 설정이 우선한다.
 *  - 클래스 전체에는 @Transactional을 안붙이고 필요한 메소드에만 해줌
 */
@Transactional
@Service("boardService")
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO boardDAO;

	public BoardServiceImpl() {
	}

	@Override
	public List<BoardVO> getBoardList(BoardVO vo) {
		log.info(vo.toString());
		List<BoardVO> boardList = boardDAO.getBoardList(vo);
		log.info("boardList.size() : " + boardList.size());
		return boardList;
	}	
	
	@Override
	public BoardVO getBoardById(BoardVO vo) {
		return boardDAO.getBoardById(vo);
	}

	/*
	 *  [트랜잭션 메소드]  
	 *  메소드에 붙은  @Transactional 어노테이션은 클래스에 붙은 것보다 우선한다.
	 *  그냥 @Transactional만 붙인 경우 기본 트랜잭션 설정이 적용된다.
	 *   
	 *  [기본 트랜잭션 설정]	    
	 *  1. 전파 설정은 PROPAGATION_REQUIRED 이다.
	 *  - 기존 트랜잭션이 없으면 새롭게 생성하고 있으면 거기에 참여한다.
	 *    기존 트랜잭션에 참여할 경우 그 트랜잭션에서 오류가 나면 부모/자식 모두 롤백된다.
	 *  2. 격리 수준은 ISOLATION_DEFAULT 이다.
	 *  3. 트랜잭션은 읽기/쓰기가 가능하다.
	 *  4. 모든 RuntimeException은 자동으로 롤백되고,
	 *     체크드 Exception(IO,SQL)은 롤백되지 않는다. 
	 *     그래서 체크드 예외를 처리하기 위해서는 트랜잭션을 걸어서 롤백을 해줘야 한다.
	 */
	@Transactional(propagation=Propagation.REQUIRED, 
					rollbackFor=Exception.class)
	@Override
	public int insertBoard(BoardVO vo) {

		int result = 0; // 영향을 받은 행의 건수
		
		/*
		 * [마이바티스 selectKey ] 
		 * - vo는 insertBoard(vo) 메소드에 파라미터로 전달된다. 
		 *   그리고 메소드가 실행되고 나면 vo 에는 애초에 갖고 있는 값과 
		 *   저장되면서 자동으로 채번된 번호까지 같이 저장되어 있다.
		 * - 정말 편리한 기능임. 마이바티스 저장시 시퀀스로 따진 번호를
		 *   여기에 넣어준다. 개발자가 이 알고리즘을 구현하려면 상당한
		 *   시간이 걸린다.
		 */
		result = boardDAO.insertBoard(vo);	
		
		log.info("저장후 result : " + result);
		
		if(result >= 1)
			log.info("1차 저장 성공");
		else 
			log.info("1차 저장 실패");
		
		log.info("vo.toString() : " + vo.toString());
		
		result = 0; // 초기화
		
		/*
		 * 위에서 이미 저장된 시퀀스를 사용하여 다시 테이블에 저장을 시도.
		 * 무결성 오류로 1차 저장한 데이터도 롤백되어 버림. 트랜잭션 적용됨.
		 */
		log.info("2차 저장 전");
		result = boardDAO.insertBoard2(vo);	
		log.info("2차 저장 후");
		
		return result;
	}
	
	@Override
	public void updateBoard(BoardVO vo) {
		boardDAO.updateBoard(vo);
	}

	@Override
	public void deleteBoard(BoardVO vo) {
		boardDAO.deleteBoard(vo);
	}
	
	@Override
	public void updateHit(BoardVO vo) {
		boardDAO.updateHit(vo);
	}
	@Override
	public int getTotalBoardCount() {
		return boardDAO.getTotalBoardCount();
	}



}
