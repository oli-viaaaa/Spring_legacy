package com.javalab.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.javalab.vo.BoardVO;
/**
 * 매퍼 인터페이스
 *  - 서비스Layer와 매퍼xml을 연결시키는 연결자 역할
 */
@Mapper
public interface BoardDAO {

	// 글 등록
	int insertBoard(BoardVO vo);
	
	// 데이터를 중복해서 저장해서 무결서 오류 유발용 메소드
	int insertBoard2(BoardVO vo);
	
	// 글 수정
	void updateBoard(BoardVO vo);

	// 글 삭제
	void deleteBoard(BoardVO vo);

	// 글 상세 조회(queryForObject 메소드 사용)
	BoardVO getBoardById(BoardVO vo);

	// 글 목록 조회(query 메소드 사용)
	List<BoardVO> getBoardList(BoardVO vo);

	// 글조회수 증가
	void updateHit(BoardVO vo);	
	
	// 전체 게시물 숫자
	int getTotalBoardCount();
	
}