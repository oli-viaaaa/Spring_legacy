package com.javalab.service;

import java.util.List;

import com.javalab.vo.RoleVo;
import com.javalab.vo.UserVo;

public interface UserService {	
	public int idCheck(String id);
	public void insertBoard(UserVo vo);	
	public List<RoleVo> getRoles();
}
