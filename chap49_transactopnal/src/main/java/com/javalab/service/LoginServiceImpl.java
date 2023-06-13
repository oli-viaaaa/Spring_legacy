package com.javalab.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalab.dao.LoginDao;
import com.javalab.vo.UserVo;

@Service("loginService")
public class LoginServiceImpl implements LoginService  {
	
	private static final Logger log = 
					LoggerFactory.getLogger(LoginServiceImpl.class);
	
	//@Resource(name = "loginDao")
	@Autowired
	private LoginDao dao;

	public LoginServiceImpl() {
	}

	public UserVo getUserById(UserVo vo) {
		log.info(vo.toString());
		return dao.getUserById(vo);
	}

}
