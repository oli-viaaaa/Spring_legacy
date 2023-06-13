package com.javalab.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalab.dao.BoardDAO;
import com.javalab.dao.UserDao;
import com.javalab.vo.BoardVO;
import com.javalab.vo.RoleVo;
import com.javalab.vo.UserVo;

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
	public void insertBoard(UserVo vo) {
		dao.insertUser(vo);
	}

	// 권한 조회
	@Override
	public List<RoleVo> getRoles() {
		return dao.getRoles();		
	}

}
