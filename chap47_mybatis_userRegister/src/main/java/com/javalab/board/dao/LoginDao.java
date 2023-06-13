package com.javalab.board.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.javalab.board.vo.UserVo;


/*
 * [매퍼 인터페이스]
 *  - 로그인 서비스와 쿼리문이 있는 매퍼XML을 연결
 *  - 서비스Impl에서 매퍼 인터페이스의 메소드를 호출하면
 *    자동으로 매퍼XML의 쿼리문 메소드가 호출됨.
 */
@Mapper
public interface LoginDao {
	// 로그인 유저 조회
	UserVo getUserByIdPwd(UserVo vo);
}