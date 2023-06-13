package com.javalab.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.javalab.vo.BoardVO;
import com.javalab.vo.Criteria;

/*
 * [매퍼 인터페이스]
 */
@Mapper
public interface BoardDAO {
	//List<BoardVO> getBoardList(BoardVO vo);		// 글 목록 조회	
	List<BoardVO> getListPagingAndSearching(Criteria cri);	// 글 목록 조회(Criteria 객체 사용)	
	int insertBoard(BoardVO vo);				// 글 등록	
	void updateBoard(BoardVO vo);				// 글 수정	
	void deleteBoard(BoardVO vo);				// 글 삭제	
	BoardVO getBoardById(BoardVO vo);			// 글 상세 조회	
	void updateHit(BoardVO vo);					// 글조회수 증가	
	int getTotalBoardCount(Criteria cri);					// 전체 게시물 숫자	
}