package com.javalab.mybatis.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.javalab.mybatis.vo.BoardVo;

/**
 * 매퍼 인터페이스
 *  - 서비스 Layer(ServiceImpl)와 매퍼.XML(쿼리문) 연결자 역할
 * @Mapper
 *  a. @Mapper 어노테이션 부착 : 이 인터페이스는 매퍼 인터페이스로 인식한다.
 *  b. root-context.xml에 다음과 같이 설정하고
 * 		<mybatis-spring:scan base-package="com.javalab.logging.dao"
 *					annotation="org.apache.ibatis.annotations.Mapper"/>
 * 	c. a + b가 되어야 비로서 BoardMapperInterface가 빈으로 생성된다. 즉 매퍼XML을 감싼
 *    프록시 객체가 빈으로 생성된다.
 * @Mapper 주석은 이 인터페이스가 MyBatis 매퍼이며 MyBatis-Spring에 의해 스캔되고 
 * 빈으로 등록됨을 나타냅니다.
 */

@Mapper
public interface BoardMapperInterface {

	// 게시물 목록 조회 메소드
	public ArrayList<BoardVo> selectBoardList();
	// 한 명의 회원 조회 메소드
	public BoardVo getBoardById(int no);
	// 회원 수정 메소드
	public int modifyBoard(BoardVo boardVo);
	// 게시물 저장 메소드
	public int insertBoard(BoardVo vo);
	// 조회수 증가 메소드
	public void updateHitCount(int no);
	// 게시물 삭제 메소드
	public int deleteBoard(int no);
}
