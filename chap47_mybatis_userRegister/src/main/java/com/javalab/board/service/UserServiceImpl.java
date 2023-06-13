package com.javalab.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalab.board.dao.UserDao;
import com.javalab.board.vo.RoleVo;
import com.javalab.board.vo.UserVo;

import lombok.extern.slf4j.Slf4j;


@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao dao;

	public UserServiceImpl() {
	}
	
	@Override
	public int idCheck(String id) {
		return dao.idCheck(id);
	}
	
	@Override
	public void insertUser(UserVo vo) {
		dao.insertUser(vo);
	}

	// 권한 조회
	@Override
	public List<RoleVo> getRoles() {
		return dao.getRoles();		
	}
	
	// 사용자 조회
	@Override
	public List<UserVo> getUserByCon(UserVo vo){
		List<UserVo> userList = dao.getUserByCon(vo);
		return userList;
	}

}
