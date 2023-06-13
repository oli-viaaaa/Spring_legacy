package com.javalab.vo;

import java.sql.Date;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * [다목적 운송 수단 : 조회/DB 처리 결과 운송 전용 Dto]
 * - 데이터 운송용 객체로서 데이터베이스에서 여러 테이블을 조인해서 결과를 받아올 경우
 *   하나의 Vo 객체에는 모두 담을 수 없기 때문에 별도의 Dto 클래스에 담아서 운송
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeCommonDto extends Employee{
	// [1] Employee에서 상속받은 멤버변수(DB컬럼)
	// Employee 에서 상속받은 컬럼들
	
	// [2] Vo가 갖고 있지 않는 추가 멤버 변수(DB컬럼 아님, 데이터운송용)
	private String jobTitle;
	private String departmentName;
	private String stateProvince;
	private String city;
	private String countryName;
	
	// [3] 요청한 페이지, 한페이지에 보여줄 게시물수 정보 보관
	private int pageNum = 1;	// 요청 페이지 번호
	private int amount = 10; 		// 한페이지에 보여줄 게시물수
}
