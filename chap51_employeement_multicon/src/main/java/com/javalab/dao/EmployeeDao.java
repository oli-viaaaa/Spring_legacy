package com.javalab.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.javalab.vo.BoardVO;
import com.javalab.vo.Criteria;
import com.javalab.vo.Employee;
import com.javalab.vo.EmployeeCommonDto;

/*
 * [매퍼 인터페이스]
 *  - 매퍼 XML과 코드를 연결해주는 역할. XML은 자바가 아니어서 객체 생성이 안되고 인터페이스를 구현할 수 없음.
 *  - 그래서 그걸 호출하기 위해서 "매퍼 인터페이스가 필요함"
 */ 
@Mapper
public interface EmployeeDao {
	public List<EmployeeCommonDto> getEmployeeList(Criteria cri);	// 전사원조회	
	//public List<EmployeeCommonDto> getEmployeeList(EmployeeCommonDto eDto);	// 전사원조회
	public int getTotalEmployees(Criteria cri);						// 페이징을 위한 사원숫자 조회	
	public void register(Employee emp);
}