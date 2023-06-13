package com.javalab.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.javalab.vo.UserVo;

/*
 * [매퍼 인터페이스]
 */
@Mapper
public interface LoginDao {
	// 로그인 유저 조회
	UserVo getUserById(UserVo vo);
}