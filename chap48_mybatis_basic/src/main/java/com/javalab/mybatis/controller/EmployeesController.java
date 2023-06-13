package com.javalab.mybatis.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javalab.mybatis.vo.Department;
import com.javalab.mybatis.vo.EmployeeCommonDto;
import com.javalab.mybatis.vo.Employees;
import com.javalab.mybatis.service.EmployeesService;

import lombok.extern.slf4j.Slf4j;

/**
 * @RequestMapping("/emp")
 *  - 호출 url에 세분화(한단계 추가) 
 *  - 컨트롤러 차원에서 여기에 오는 모든 메소드의 호출 Url앞에 자동으로
 *    "/emp"를 붙여주는 기능을 함.
 *  - 나중에 모듈이 많아질 경우 각 모듈별로 분리하기 위해서 한단계 세분화된
 *    url이 필요함.
 *  - 스프링 인터셉터로 로그인을 했는지 자동으로 체크하기 위해서도 필요함.  
 *
 */
@RequestMapping("/emp")
@Controller
@Slf4j
public class EmployeesController {

	// [의존성주입.1] 일반적인 방식
	// @Autowired
	// private EmployeesService employeesService;

	/**
	 * [의존성주입.2] 생성자 의존성 주입
	 */
	private final EmployeesService empService;
	// 생성자
	public EmployeesController(EmployeesService employeesService) {
		this.empService = employeesService;
	}

	/*
	 * 현재 클래스의 모든 메소드가 호출될 때 본 메소드는 가장 우선해서 호출됨.
	 * 여러 화면에 공통적으로 들어가는 부분을 메소드마다 구현하면 코드 중복됨.
	 * 그걸 예방하는 차원에서 필요함. 하지만 강제사항은 아님 다른 방법도 가능.
	 * 날짜, 부서정보는 여러 메소드에서 공통적으로 사용할 가능성이 있으므로
	 * 본 메소드에서 공통으로 만들어주고 다른 메소드들은 사용만 하면됨.
	 */
	@ModelAttribute("empVo")
	public Map<String, Object> callFirstofAll() {

		log.info("callFirstofAll() 메소드");

		Map<String, Object> empVo = new HashMap<String, Object>();

		// 오늘 날짜 정보
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String hireDate = format1.format(today);
		empVo.put("hireDate", hireDate);

		// 부서 정보 조회
		List<Department> getDepartmentList = empService.getDepartmentList();
		log.info("getDepartmentList.size() " + getDepartmentList.size());

		// 오늘날짜와 부서 정보를 Map 객체에 담아서 리턴
		// JSP에서 사용하면 됨.
		empVo.put("departmentList", getDepartmentList);

		return empVo;
	}

	// 사원 목록 조회
	@GetMapping("/list")
	public String getEmployeesListDto(Employees employee, Model model) {
		List<EmployeeCommonDto> employeeList = empService.getEmployeesList(employee);
		model.addAttribute("employeeList", employeeList);
		log.info("employeeList.size() : " + employeeList.size());
		for (EmployeeCommonDto employeeCommonDto : employeeList) {
			log.info(employeeCommonDto.toString());
		}
		return "/employeeList"; // employeeList.jsp
	}

	// 사원 상세 보기
	@GetMapping("/read")
	public String getEmployees(@RequestParam("employeeId") Integer employeeId, Model model) {
		Employees employees = empService.getEmployees(employeeId);
		model.addAttribute("employees", employees);
		return "/employee"; // employee.jsp
	}

	/*
	 * 사원 등록 폼을 띄워주는 메소드
	 * @ModelAttribute("employees") Employees employees
	 *  - 사원등록 폼은 <form:form> 태그로 만들어져 있다. 처음 화면이 열릴때
	 *    기본적인 값이 넘어가야 한다. 위와 같이 하면 텅빈 커맨드 객체가 만들어지고
	 *    JSP 화면으로 전달되고 빈값으로 채워진다. 물론 된다.
	 */
	@GetMapping("/form")
	public String insertForm(@ModelAttribute("employees") Employees employees, 
							Model model) throws IOException {
		return "/insertForm"; // insertForm.jsp
	}

	/*
	 * [사원 등록 처리 메소드]
	 * @ModelAttribute("employees") Employees employees
	 *  - 이렇게 해주면 혹시 사원정보에 문제가 있어서 다시 사원 정보입력폼으로
	 *    돌아가게 된다면 기존에 입력했던 값들을 다시 보여줘야 한다. 그렇게
	 *    하기 위해서 필요함. 그냥 Employees employees해도 되지만
	 *    이름을 내가 원하는 걸로 할때 필요. 지금은 이름이 employees로
	 *    둘다 같기 때문에 사실 무의미함.
	 */
	@PostMapping("/register")
	public String insertEmployees(@ModelAttribute("employees") Employees employees) {

		int result = empService.insertEmployees(employees);
		
		if (result == 1) {
			log.info("저장 성공");
			return "redirect:/emp/list";
		} else {
			log.info("저장 실패");
			return "/form";
		}
	}
	
	/*
	 * 다양한 조건으로 사원 검색
	 */
	@GetMapping("/getEmployeeByCon")
	public String getEmployeeByCon(EmployeeCommonDto employeeCommonDto, Model model) {
		List<EmployeeCommonDto> employeeList = empService.getEmployeeByCon(employeeCommonDto);
		model.addAttribute("employeeList", employeeList);
		return "/employeeList";
	}	
	
}