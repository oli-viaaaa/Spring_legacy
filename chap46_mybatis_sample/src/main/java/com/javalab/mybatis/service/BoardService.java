package com.javalab.mybatis.service;

import java.util.ArrayList;

import com.javalab.mybatis.vo.BoardVo;



/**
 * 서비스 인터페이스
 *  - BoardServiceImple 클래스의 인터페이스로 BoardServiceImple 클래스를 대신하여
 *    외부에 노출되어 BoardServiceImple의 메소드를 사용할 수 있는 창구 역할을 한다.
 *
 */
public interface BoardService {

	// 게시물 목록 조회 메소드
	ArrayList<BoardVo> selectBoardList();

	// 게시물 삭제 메소드
	int deleteBoard(int no);

	// 한 명의 회원 조회 메소드
	BoardVo getBoardById(int no);

	// 회원 수정 메소드
	int modifyBoard(BoardVo boardVo);

	// 게시물 저장 메소드
	int insertBoard(BoardVo vo) throws Exception;

	// 조회수 증가 메소드(게시물이 한 번 읽힐때마다 증가)
	void updateHitCount(int no);

}