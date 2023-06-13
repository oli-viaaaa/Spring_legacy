package com.javalab.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.javalab.mybatis.vo.Department;
import com.javalab.mybatis.vo.EmployeeCommonDto;
import com.javalab.mybatis.vo.Employees;

/**
 * [매퍼 인터페이스]
 *  - 서비스 Layer와 매퍼 xml의 쿼리문을 연결해주는 가교(Bridge) 역할
 *  - @Mapper : 이 패키지에 있는 인터페이스 중에서 @Mapper 어노테이션이 있는 인터페이스만이 진정한 매퍼 인터페이스라는 선언
 *
 */
@Mapper
public interface EmployeesMapper {
	
	// 사원 목록 조회
	public List<EmployeeCommonDto> getEmployeesList(Employees emp);
	
	// 사원 한명 조회
	public Employees getEmployees(Integer employeeId);
	
	// 사원 등록
	public int insertEmployees(Employees emp);
	
	// 부서 목록 조회
	public List<Department> getDepartmentList();
	
	// 다양한 조건으로 검색
	public List<EmployeeCommonDto> getEmployeeByCon(EmployeeCommonDto employeeCommonDto);

}
