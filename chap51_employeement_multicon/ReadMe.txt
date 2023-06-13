[chap21_mybatis03_employeement 특이사항]

 1. 오라클 기본 제공 HR 데이터베이스 활용
 2. Common Dto : vo 만으로도 
 
 
 
[주요기술]

 1. 부트스트랩 템플릿

[진행단계]

[데이터베이스 작업]

1. 기존에 만들어놓은 hr2 데이터베이스 사용
 
2. 추가 테이블 생성 및 기초 데이터 import
 - 로그인, 사원, 게시판 테이블은 이전 프로젝트 참조해서 생성
 
3. 사원 목록 쿼리문 생성

 1) 1차 쿼리문
 
    select e.employee_id, e.first_name, e.last_name, e.email, e.phone_number, e.hire_date, e.salary,
        e.job_id, j.job_title, e.department_id, d.department_name,
        d.location_id, l.state_province, l.city,
        l.country_id, c.country_name
    from employees e left outer join jobs j on e.job_id = j.job_id
        left outer departments d on e.department_id = d.department_id
        left outer join locations l on d.location_id = l.location_id
        left outer join countries c on l.country_id = c.country_id
	order by employee_id asc;
 
 2) 2차 쿼리문 오라클 힌트와 인덱스 사용해서 정렬
 
	select a.employee_id, a.first_name, a.last_name, a.email, a.phone_number, a.hire_date, a.salary,
	        a.job_id, a.job_title, a.department_id, a.department_name,
	        a.location_id, a.state_province, a.city,
	        a.country_id, a.country_name
	from(
	        select /*+ INDEX_DESC(employees EMP_EMP_ID_PK) */  rownum rn,
	        e.employee_id, e.first_name, e.last_name, e.email, e.phone_number, e.hire_date, e.salary,
	        e.job_id, j.job_title, e.department_id, d.department_name,
	        d.location_id, l.state_province, l.city,
	        l.country_id, c.country_name
	        from employees e left outer join jobs j on e.job_id = j.job_id
	        left outer departments d on e.department_id = d.department_id
	        left outer join locations l on d.location_id = l.location_id
	        left outer join countries c on l.country_id = c.country_id
	      where 1=1
			and (e.first_name like '%'||''||'%' or e.last_name like '%'||''||'%')
	        and rownum <= 10
	)a
	where rn > 0;

[프로젝트]

1. 프로젝트 생성 : chap21_mybatis03_employeement

2. pom.xml 기존 프로젝트 복사

3. servlet-context.xml, root-context.xml 복사

4. 부트스트랩 복사 webapp/resources 폴더 복사

5. webapp/views 폴더 복사

6. 자바 패키지 복사

7. src/main/resources 하위에 있는 config, mapper 폴더 복사

-----------------------------------------------------------

8. com.javalab.vo 패키지에 
 - Employee 클래스 추가

9. com.javalab.dto 패키지 생성
 - EmployeeCommonDto 클래스 생성

10. com.javalab.util 패키지에
 - RefreshableSqlSessionFactoryBean 확인 

11. sqlMapConfig.xml 두 vo, dto Alias 추가

12. EmployeeMapper.xml 생성
 - getEmployeeList() SQL 쿼리 메소드 생성
 - getTotalEmployees() SQL 쿼리 메소드 생성

13. Dao Interface
 - EmployeeDao
 
14. Service + ServiceImpl
 - 인터페이스 먼저 생성후 Impl 구현
 
15. HomeController 수정
 - 

6. empList.jsp 화면 생성

 1) 검색폼 추가하고 화면 확인
 
 2) 컬럼 추가 : 
   

7. 왼쪽 부트스트랩 메뉴명 수정

 	  
 3) 페이지네이션 코드 추가
 
 4) 페이지네이션 폼 추가
 
 5) 자바스크립트 추가

4. 페이지네이션 기능 작동하면 다음 내용 설명
 1) searchForm 폼에서는 검색 키워드만 갖고 서버로 간다. 
       기존에 갖고 있는 페이징 정보는 필요 없다. 
       어차피 검색된 결과로 새로운 페이징 정보가 만들어지기 때문이다.

5. 목록 화면에서 게시물 제목 눌러서 해당 게시물 상세조회
 - boardView.jsp 작업
  
6. 게시물 등록 부분 전환
 
 
  


8. C/R/U/D 로그인페이지까지 완성후 다음 단계

 1) 데이터테이블의 역할과 어떻게 사용하는지 설명
 2) 부트스트랩 컬럼 분할 알고리즘 설명(col-md-3)
 3) class에 form-control를 넣으면 기본 형태가 나옵니다.


9. 조건이 여러 가지일 경우의 마이바티스 조건문 추가 설명
 1) SQL문 수정
  
10. 쿼리문 수정시 서버 재시작 없이 적용시키는 방법 설명 RefreshableSqlSessionFactoryBean
 - RefreshableSqlSessionFactoryBean 클래스 생성
 - root-context.xml수정
	<bean id="sqlSessionFactory" 
				class="com.javalab.util.RefreshableSqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource" />
		<property name="configLocation" value="classpath:/config/sqlMapConfig.xml" /> <!-- 별명 파일 -->
		<!-- SQL 쿼리문(mapper 파일)이 있는 파일의 경로 설정  ~/mapper/**/*.xml 일괄 등록해도 됨-->
		<property name="mapperLocations" value="classpath:/mapper/oracle/*Mapper.xml" />
		<property name="interval" value="1000" />
	</bean>
 - 톰캣에서 자동 reload 옵션 해제	


11. ResultMap[인보이스 프로젝트할 때 설명]

 