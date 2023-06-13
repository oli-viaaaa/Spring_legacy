package com.javalab.service;

import java.util.List;

import com.javalab.vo.Criteria;
import com.javalab.vo.Employee;
import com.javalab.vo.EmployeeCommonDto;


public interface EmployeeService {
	public List<EmployeeCommonDto> getEmployeeList(Criteria cri);	// 전사원조회	
	//public List<EmployeeCommonDto> getEmployeeList(EmployeeCommonDto eDto);	// 전사원조회
	public int getTotalEmployees(Criteria cri);			// 페이징을 위한 사원숫자 조회	
	public void register(Employee emp);
}

