package com.javalab.mybatis.service;

import java.util.List;

import org.apache.ibatis.binding.MapperProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalab.mybatis.dao.EmployeesMapper;
import com.javalab.mybatis.vo.Department;
import com.javalab.mybatis.vo.EmployeeCommonDto;
import com.javalab.mybatis.vo.Employees;

import lombok.extern.slf4j.Slf4j;

/*********************************************************
 * Service Impl Class
 * 	- EmployeesService 인터페이스를 구현한 클래스
 *  - EmployeesDao 필드 : Dao 인터페이스 타입 멤버 변수(필드) 의존성주입
 *    실제로는 MapperProxy 객체가 주입된다. 그 MapperProxy를 통해서 매퍼xml의
 *    쿼리를 실행할 수 있다. 쿼리의 실행과 결과 반환은 SqlSessionTemplate이 한다.
 *  - org.apache.ibatis.binding.MapperProxy
 *  - SqlSessionTemplate은 처리후 자동 커밋/롤백 되기 때문에 다른 스레드가 와서
 *    실행해도 아무런 문제가 없다.
 * @author magicoh
 *********************************************************/
@Service
@Slf4j
public class EmployeesServiceImpl implements EmployeesService {

	// [의존성주입 방법.1] @Autowired를 통한 의존성 주입
	//@Autowired
	//private EmployeesDao dao;
	
	/**
	 * [의존성주입 방법.2] 생성자 의존성 주입
	 * 	- 매퍼 인터페이스 타입으로
	 */
	private final EmployeesMapper dao; // 매퍼 인터페이스 Type 변수
	public EmployeesServiceImpl(EmployeesMapper dao) {
		this.dao = dao;
	}

	// 사원 목록 조회
	@Override
	public List<EmployeeCommonDto> getEmployeesList(Employees emp) {
		List<EmployeeCommonDto> employeesList = dao.getEmployeesList(emp);
		return employeesList;
	}

	// 사원 조회
	@Override
	public Employees getEmployees(Integer employeeId) {
		Employees employees = dao.getEmployees(employeeId);
		return employees;
	}
	
	// 사원 등록
	@Override
	public int insertEmployees(Employees emp) {
		int result = dao.insertEmployees(emp);
		return result;
	}

	@Override
	public List<Department> getDepartmentList() {
		List<Department> deptList = dao.getDepartmentList();
		return deptList;
	}

	/*
	 * 다양한 조건으로 검색
	 */
	@Override
	public List<EmployeeCommonDto> getEmployeeByCon(EmployeeCommonDto employeeCommonDto) {
		List<EmployeeCommonDto> employeeList = dao.getEmployeeByCon(employeeCommonDto);
		return employeeList;
	}



}