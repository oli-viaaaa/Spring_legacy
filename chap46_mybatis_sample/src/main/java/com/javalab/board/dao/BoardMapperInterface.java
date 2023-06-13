package com.javalab.board.dao;

import java.util.ArrayList;

import com.javalab.board.vo.BoardVo;

/*
 * [매퍼 인터페이스]
 * 	- 서비스 Layer(ServiceImpl)와 매퍼.XML(쿼리문) 연결자 역할 
 */

public interface BoardMapperInterface {

	// 게시물 목록 조회 메소드
	public ArrayList<BoardVo> selectBoardList();

	// 게시물 삭제 메소드
	public int deleteBoard(int no);

	// 한 명의 회원 조회 메소드
	public BoardVo getBoardById(int no);

	// 회원 수정 메소드
	public int modifyBoard(BoardVo boardVo);

	// 게시물 저장 메소드
	public int insertBoard(BoardVo vo);

	// 조회수 증가 메소드(게시물이 한 번 읽힐때마다 증가)
	public void updateHitCount(int no);
	
}
