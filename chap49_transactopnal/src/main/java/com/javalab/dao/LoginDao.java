package com.javalab.dao;

import org.apache.ibatis.annotations.Mapper;
import com.javalab.vo.UserVo;

/**
 * 매퍼 인터페이스
 *  - 서비스Layer와 매퍼xml을 연결시키는 연결자 역할
 */
@Mapper
public interface LoginDao {
	// 로그인 유저 조회
	UserVo getUserById(UserVo vo);
}