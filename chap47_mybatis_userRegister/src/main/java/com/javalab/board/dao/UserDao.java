package com.javalab.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.javalab.board.vo.RoleVo;
import com.javalab.board.vo.UserVo;

/*
 * [매퍼 인터페이스]
 */
@Mapper
public interface UserDao {

	// 회원가입
	// Mapper에 있는 id와 동일해야함
	void insertUser(UserVo vo);
	
	// 회원중복 여부 체크
	int idCheck(String id);

	// 권한 코드 조회
	List<RoleVo> getRoles();
	
	public List<UserVo> getUserByCon(UserVo vo);
	
}