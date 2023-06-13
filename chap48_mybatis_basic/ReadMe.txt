[chap20_mybatis01_userRegister 특이사항]

 1. 기존의 로그인 게시판에 회원가입 기능 추가
 2. 수강생들이 직접 패키지 만들기(com.javalab.controller 안씀)
 3. 하나의 컨트롤러에서 다른 컨트롤러를 사용해야 하는 경우도 자주 있음.
 4. [생성자를 통한 의존성 주입] UserController 에서 생성자 주입 구현
  1) @AllArgsConstructor 어노테이션 사용
   - 멤버 변수를  UserService, LoginService, BoardService
   - 이렇게 하면 어노테이션을 사용하지 않아도 자동으로 주입됨.(기본생성자 없어야함)
  2) BoardController에서도 생성자 의존성 자동 주입 처리함.
  
[주요기술]

 1. 마이바티스 게시판 프로젝트
 2. HikariCP 커넥션 풀 사용
 3. logback.xml 로그 적용
 4. log4jdbc-log4j2(SQL Log 적용)
 5. 스프링 인터셉터 기능 적용
 6. mapUnderscoreToCamelCase : 
   - 테이블의 컬럼명(role_id)과 Vo의 멤버변수(roleId) 이름이 달라도 매칭해줌
 7. 화면을 띄울 때 데이터베이스에서 코드를 불러와서 특정 부분(select option)을
       만들어주는 기능. 예) 관리자/일반사용자 여부(권한)   
 

[진행단계]

1. 기존 프로젝트에서 이름 바꿔서 프로젝트 생성 chap20_mybatis01_userRegister(Spring Legacy Project)
 - 기존 프로젝트에 _rest라고 붙여서 프로젝트 이름 작성
 - com.수강생이름.controller 패키지 
 
2. 프로젝트 설정 변경, pom.xml 버전 수정

3. 로그 logback.xml 적용

4. pom.xml에 디펜던시(의존성) 추가할 내용들

        <!-- logback 로그 관련 의존성 -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
            <scope>runtime</scope>
        </dependency>  				
		<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
		<dependency>
		    <groupId>org.projectlombok</groupId>
		    <artifactId>lombok</artifactId>
		    <version>1.18.22</version>
		    <scope>provided</scope>
		</dependency>

		<!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-test</artifactId>
		    <version>5.0.7.RELEASE</version>
		    <scope>test</scope>
		</dependency>
		<!-- Test -->
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.13</version>
			<scope>test</scope>
		</dependency>  	
			
		<!-- HikariCP Connection Pool -->
		<dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>2.6.3</version>
        </dependency>
        
		<!-- oracle -->
		<dependency>
			<groupId>com.oracle.ojdbc</groupId>
			<artifactId>ojdbc8</artifactId>
			<version>19.3.0.0</version>
		</dependency>	
		<!-- KO16MSWIN949 같은 비유니코드 문자집합 사용시 orai18n 패키지 추가 필요. -->
		<dependency>
			<groupId>com.oracle.ojdbc</groupId>
			<artifactId>orai18n</artifactId>
			<version>19.3.0.0</version>
		</dependency>		
		
		<!-- MyBatis Configuration -->
		<dependency>
		    <groupId>org.mybatis</groupId>
		    <artifactId>mybatis</artifactId>
		    <version>3.4.1</version>
		</dependency>		
		<dependency>
		    <groupId>org.mybatis</groupId>
		    <artifactId>mybatis-spring</artifactId>
		    <version>1.3.0</version>
		</dependency>	
		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-jdbc</artifactId>
		    <version>5.0.7</version>
		</dependency>	
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-tx -->
		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-tx</artifactId>
		    <version>5.2.9.RELEASE</version>
		</dependency>	

		<!-- ajax 호출을 위한 디펜던시 [꼭 확인!]-->
		<dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-databind</artifactId>
		    <version>2.13.4</version>
		</dependency>

5. root-context.xml 수정
 
 
[권한 관련 기능 추가]

1. 데이터베이스 작업

 1) 데이터베이스에 권한 관련 role_tbl 테이블 추가
 	--drop table role_tbl cascade constraints;
	create table role_tbl(
	    role_id varchar2(20) constraint role_pk primary key,
	    role_name varchar2(20) not null  
    );    
	insert into role_tbl(role_id, role_name) values('admin', '관리자');    
	insert into role_tbl(role_id, role_name) values('user', '일반사용자');    
  	commit;
  	    
 2) user_tbl 테이블 수정 - role 컬럼 삭제하고 role_id 컬럼 추가하고 외래키 제약 추가
	-- user_tbl 수정
	-- 기존 테이블에  role_id 추가  
	alter table user_tbl add role_id varchar(20);
	
	-- 추가된 role_id에 외래키 제약 조건 추가
	alter table user_tbl add constraint role_id_fk foreign key(role_id) references role_tbl(role_id);
	
	-- role 컬럼 삭제
	alter table user_tbl drop column role ;

 3) UserVo 클래스 멤버 변수명 변경 role -> roleId
 	@Data
	public class UserVo {
		private String id;
		private String pwd;
		private String name;
		private String email;
		private String roleId;	
		private Date joinDate;	
	}
 
2. RoleVo Vo 클래스 생성
	@Data
	public class RoleVo {
		private String roleId;
		private String roleName;
	}
 
3. sqlMapConfig.xml 수정

 1) <typeAlias type="com.javalab.vo.RoleVo" alias="Role" />
 
 2) <!-- 테이블의 컬럼명(role_id)과  Vo의 멤버변수(roleId) 매칭해주는 역할 -->
	<setting name="mapUnderscoreToCamelCase" value="true"/>
 
 
4. userMapper.xml 권한 코드 조회 메소드 수정

 1) insertUser 메소드 수정
 	<insert id = "insertUser" parameterType="User">
		insert into user_tbl(
			id, 
			pwd, 
			name, 
			email, 
			role_id, /* role -> role_id */
			joindate) 
		values(
			#{id}, 
			#{pwd}, 
			#{name}, 
			#{email},
			#{roleId},			 
			sysdate)
	</insert>
	
 2)	<!-- 권한(Role) 조회 -->
	<select id = "getRoles" resultType="Role">
		select role_id, role_name from role_tbl
	</select>

 //3) 2)번 getRoles 메소드에서 mapUnderscoreToCamelCase 프로젝트 실행후 설명

5. 매퍼 인터페이스(interface UserDao)에 권한 메소드 추가 	
  	// 권한 코드 조회
	List<RoleVo> getRoles();
	
6. 서비스 단 작업 
 1) interface UserService 추가
	public List<RoleVo> getRoles();
 
 2) UserServiceImpl
	// 권한 조회
	@Override
	public List<RoleVo> getRoles() {
		return dao.getRoles();		
	}	

	
8. UserController 추가
	// 회원 등록 메소드(핸들러)
	@PostMapping("/join.do")
	public String insertUser(UserVo vo, Model model) throws IOException{  // 수정
		log.info("insertUser post 메소드 vo.toString() " + vo.toString());
		service.insertUser(vo);	// 회원 등록(저장)
		return "redirect:/board/boardList.do"; // 게시물 목록 페이지 컨트롤러 호출
	}
 
9. joinForm.jsp 추가 권한 관련 select option 추가

            <td width="400">					
            	<select class="form-control" name="client_id" id="client_id" >
					<c:forEach var="role" items="${roleList}">
						<option value="${role.roleId }">${role.roleName }</option>
					</c:forEach>	
				</select>
			</td>
 
[회원가입 관련]

1. 아이디 중복 체크 ajax 호출로 구현

 1) ajax 호출을 위한 디펜던시 추가 pom.xml
		<!-- ajax 호출을 위한 디펜던시 -->
		<dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-databind</artifactId>
		    <version>2.13.4</version>
		</dependency>  
 
2. joinForm.jsp에 ajax 호출 자바스크립트 jQuery 구현


[mapUnderscoreToCamelCase 설명]

1. 프로그램 정상 실행후에 다음 사항 반드시 설명[중요]

3) sqlMapConfig.xml에서 다음 사항 주석처리 하고 실행해보면 role 관련해서 오류남
 - 주석처리 :  mapUnderscoreToCamelCase 설명]
 
 
 