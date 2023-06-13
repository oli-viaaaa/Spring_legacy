package com.javalab.board.service;

import java.util.List;

import com.javalab.board.vo.RoleVo;
import com.javalab.board.vo.UserVo;


public interface UserService {	
	public int idCheck(String id);
	public void insertUser(UserVo vo);	
	public List<RoleVo> getRoles();
	// 사용자 검색
	public List<UserVo> getUserByCon(UserVo vo);
}
