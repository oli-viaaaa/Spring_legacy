package com.javalab.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.javalab.vo.RoleVo;
import com.javalab.vo.UserVo;

/*
 * [매퍼 인터페이스]
 */
@Mapper
public interface UserDao {

	// 회원가입
	void insertUser(UserVo vo);
	
	// 회원중복 여부 체크
	int idCheck(String id);

	// 권한 코드 조회
	List<RoleVo> getRoles();
	
	
	
}