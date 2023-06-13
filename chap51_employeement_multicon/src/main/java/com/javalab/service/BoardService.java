package com.javalab.service;

import java.util.List;

import com.javalab.vo.BoardVO;
import com.javalab.vo.Criteria;


public interface BoardService {
	
	//List<BoardVO> getBoardList(BoardVO vo);		// 글 목록 조회
	List<BoardVO> getListPagingAndSearching(Criteria cri);	// 글 목록 조회(페이징)
	BoardVO getBoardById(BoardVO vo);			// 글 상세 조회
	int insertBoard(BoardVO vo);				// 글 등록
	void updateBoard(BoardVO vo);				// 글 수정
	void deleteBoard(BoardVO vo);				// 글 삭제
	void updateHit(BoardVO vo);					// 글조회수 증가
	int getTotalBoardCount(Criteria cri);					// 전체 게시물 숫자
}

