package com.javalab.service;

import java.util.List;

import com.javalab.vo.BoardVO;


public interface BoardService {

	// 게시물 등록
	int insertBoard(BoardVO vo);

	// 게시물 수정
	void updateBoard(BoardVO vo);

	// 게시물 삭제
	void deleteBoard(BoardVO vo);

	// 게시물 상세 조회
	BoardVO getBoardById(BoardVO vo);

	// 게시물 목록 조회
	List<BoardVO> getBoardList(BoardVO vo);

	// 게시물 조회수 증가
	void updateHit(BoardVO vo);	
	
	// 전체 게시물 숫자
	int getTotalBoardCount();
}

