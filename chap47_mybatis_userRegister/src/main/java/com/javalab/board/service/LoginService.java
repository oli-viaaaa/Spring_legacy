package com.javalab.board.service;

import com.javalab.board.vo.UserVo;

public interface LoginService {
	UserVo getUserByIdPwd(UserVo vo);
}