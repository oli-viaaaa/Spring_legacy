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

import com.javalab.mybatis.vo.Employees;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Slf4j
public class EmployeesSessionTest {
	
	/**
	 * root-context.xml에 빈으로 등록해놓은 SqlSessionTemplate 의존성 주입
	 *  - Spring 관리 SqlSession에서는 수동 커밋/롤백이 허용되지 않고 모두 자동 처리.
	 */
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	// 쿼리문이 모여있는 매퍼.xml의 namespace
	private static String namespace = "com.javalab.mybatis.dao.EmployeesMapper";

	
	//========================= SqlSessionTemplate 직접 사용 ========================//
	//====================== 쿼리문은 매퍼.xml에 존재 + 거기에 파라미터 전달 =================//
	
	/**
	 * SqlSessionTemplate : root-context.xml에 설정한 SqlSessionTemplate Bean이 주입됨.
	 */
	@Test @Ignore
	public void testSqlSessionTemplate() {
		assertNotNull(sqlSession);
		log.info("sqlSession : " + sqlSession.toString());
	}

	
	/**
	 * 사원 목록 조회
	 *  - SqlSessionTemplate 사용
	 */
	@Test @Ignore
	public void testGetEmpListSession() {
		//selectList(매퍼인터페이스 이름.쿼리ID, 목록조회할때 조건 파라미터로 현재는 형식적 전달)
		List<Employees> empList = sqlSession.selectList(namespace + ".getEmployeesList"); 
		//assertNotNull(empList);
		assertThat(empList.size()).isGreaterThan(0);	// 단정메소드로 empList 크기 > 0 인지 검증
		empList.forEach(emp->log.info(emp.toString()));		
	}	
	
	/**
	 * 사원 한명 조회
	 *  - SqlSessionTemplate 사용
	 *  - org.assertj의 assertThat 사용해서 비교(junit 아님)
	 */
	@Test @Ignore
	public void testGetEmpSession() {
		// selectOne(매퍼인터페이스이름.쿼리ID,  파라미터-실제 employee_id)
		Employees employees = sqlSession.selectOne(namespace + ".getEmployees", 100);	
		assertThat(employees.getEmployeeId()).isEqualTo(100);
		log.info(employees.toString());
	}	
	

	/**
	 * 사원 등록/저장
	 *  - SqlSessionTemplate 사용
	 */
	@Test //@Ignore
	@Commit
	public void testInserTEmpSession() {
		Employees emp = new Employees();
		emp.setLastName("홍");
		emp.setFirstName("길동");
		emp.setEmail("gildongHong@korea.com");
		emp.setPhoneNumber("010-1234-5678");
		emp.setHireDate("2023-05-27");
		emp.setJobId("AC_MGR");
		emp.setSalary(500000);
		emp.setCommissionPct(3);
		emp.setManagerId(102);
		emp.setDepartmentId(90);
		
		int result = sqlSession.insert(namespace + ".insertEmployees", emp);
		assertThat(result).isGreaterThan(0);	// 단정메소드로 empList 크기 > 0 인지 검증
	}	
		
	
}
