package com.javalab.mybatis.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.javalab.mybatis.vo.EmployeeCommonDto;
import com.javalab.mybatis.vo.Employees;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Slf4j
public class EmployeesMapperTest {
	
	/*
	 * 의존성 주입 @Autowired와 동일한 기능
	 * 매퍼 인터페이스 타입으로 의존성 주입 
	 */
	@Inject	
	private EmployeesMapper dao;
		
	/**
	 * EmployeesMapper 객체 생성 여부 검증
	 *  - 인터페이스이지만 주입된 객체는 MapperProxy@4938 객체임.
	 *  - 매퍼 인터페이스와 매퍼XML을 연결하는 대리 객체 성격.
	 */
	@Test @Ignore
	public void testEmployeesDao() {
		assertNotNull(dao);
		log.info("dao : " + dao.toString());
	}
	
	/**
	 * 사원 목록 조회
	 */
	@Test @Ignore
	public void testGetEmployeesList() {
		// [1] 조회할 객체 생성
		Employees vo = new Employees();
		
		// [2] 게시물 목록 조회
		List<EmployeeCommonDto> empList = dao.getEmployeesList(vo);
		assertNotNull(empList);
		empList.forEach(emp->log.info(emp.toString()));		
	}

	/**
	 * EmployeesDao를 사용한 사원 한명 조회
	 */
	@Test @Ignore
	public void testGetEmployees() {
		// [1] 조회할 객체 생성
		Employees vo = new Employees();
		vo.setEmployeeId(100);	// 데이터베이스에 100번 사원 조회
		
		// [2] 사원 한 명 조회
		Employees emp = dao.getEmployees(vo.getEmployeeId());
		//assertEquals(100, emp.getEmployeeId()); // 멤버변수가 Integer 타입이면 100과 비교 곤란. 다음과 같이 비교		
		assertThat(emp.getEmployeeId()).isEqualTo(100);// 조회해온 사원 번호로 검증
		log.info(emp.toString());
	}
	

	/**
	 * 사원 등록/저장
	 */
	@Test @Ignore
	@Commit
	public void testInserTEmp() {
		Employees emp = new Employees();
		emp.setLastName("정");
		emp.setFirstName("길동");
		emp.setEmail("gildongJung@korea.com");
		emp.setPhoneNumber("010-1234-5678");
		emp.setHireDate("2022-05-27");
		emp.setJobId("IT_PROG");
		emp.setSalary(3500000);
		emp.setCommissionPct(5);
		emp.setManagerId(103);
		emp.setDepartmentId(80);
		
		int result = dao.insertEmployees(emp); 
		assertThat(result).isEqualTo(1);	// 정상 저장 처리시 1 리턴
	}	
	
	/**
	 * 다양한 조건으로 사원 조회
	 *  - 사원명
	 *  - 부서명
	 *  - 입사일자 등 조건으로 조회
	 */
	@Test @Ignore
	public void testGetEmployeesListByCon() {
		// [1] 조건을 담을 객체로 EmployeeCommonDto 사용
		EmployeeCommonDto dto = new EmployeeCommonDto();
		
		// [2] 조건값 세팅
		dto.setFirstName("트레나");			// 이름 조건
		dto.setDepartmentName("Shipping"); 	// 부서 조건
		dto.setHireDateFrom("1980-01-01"); 	// 입사일From
		dto.setHireDateTo("1993-12-31");	// 입사일To
		
		// [3] 다양한 조건으로 게시물 조회 메소드 호출
		List<EmployeeCommonDto> empList = dao.getEmployeeByCon(dto);
		assertNotNull(empList);
		
		for (EmployeeCommonDto employeeCommonDto : empList) {
			log.info(employeeCommonDto.toString());			
		}		
	}	
	
	// 여러개의 객체를 컨트롤러에 바인딩 시키는 방법
	// commonDtoList 클래스를 만들어서 받는 방법
	
}
