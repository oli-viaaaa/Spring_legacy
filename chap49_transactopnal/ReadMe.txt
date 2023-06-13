[특이사항]

1. 스프링 트랜잭션
2. 스프링 인터셉터
3. 마이바티스 설정, 다른형태인 sqlMapConfig 파일 사용
4. selectKey : 저장한 키번호 알아오는 방법


 [트랜잭션]
 
 1. pom.xml

		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-jdbc</artifactId>
		    <version>${org.springframework-version}</version>
		</dependency>
		
		<!-- 스프링 트랜잭션(스프링 프레임워크 버전에 맞게) -->
		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-tx</artifactId>
		    <version>${org.springframework-version}</version>
		</dependency>

2. 어노테이션 방식의 트랜잭션 설정  root-context.xml 수정(특히 다음 코드)

	<!-- 트랜잭션 설정 -->	
	<!-- [1] XML설정이 아닌 @Transactional 어노테이션 기반의 트랜잭션 사용을 위한 선언. 
		- 자바 코드의 클래스/메소드에 @Transactional 어노테이션을 선언하여 트랜잭션 제어.
	-->
	<tx:annotation-driven />	

	 <!-- [2] 트랜잭션 메니저 정의 -->
     <bean id="transactionManager"
           class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
         <property name="dataSource" ref="dataSource" />
     </bean>

	<!-- [3] 트랜잭션 사용시 필요했던 xml 설정이 필요없음. -->
	<!-- 1. <tx:advice> 설정 불필요 -->
	<!-- 2. <aop:config> Transaction Advisor 포인트컷 설정 불필요 -->


3. sqlMapConfig.xml 마이바티스 환경설정 파일 사용(원래 버전)
 - 여기에는 typeAliasesPackage 와 mapUnderscoreToCamelCase 설정.

	<!-- [SqlSessionFactoryBean] mybatis main config - SqlMapConfig.xml(typeAliases) and mapper file -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource" />
		<property name="configLocation" value="classpath:/config/sqlMapConfig.xml" />
		<!-- 런타임시 SqlSessionFactory에 MyBatis mapper 파일들의 경로가 설정된다. ~/mapper/**/*.xml 일괄 등록해도 됨-->
		<property name="mapperLocations" value="classpath:/mapper/oracle/*Mapper.xml" />
	</bean>

4. sqlMapConfig.xml 파일 생성
<configuration>
	<settings> 
		<!-- 테이블의 컬럼명(role_id) Vo의 멤버변수(roleId) 매칭해주는 역할 -->
		<setting name="mapUnderscoreToCamelCase" value="true"/>
	</settings>
	
	<typeAliases>
		<typeAlias type="com.javalab.vo.UserVo" alias="User" />
		<typeAlias type="com.javalab.vo.BoardVO" alias="Board" />
	</typeAliases>


 [스프링 인터셉터 설정]
 
 1. 스프링 인터셉터 설정 : servlet-context.xml
 
	<!-- 인터셉터 객체 빈생성(인터셉터 클래스 경로 지정) -->
	<beans:bean id="loginInterceptor" 
		class="com.javalab.interceptor.LoginInterceptor">
	</beans:bean>
	
	<!-- Interceptor 설정 
		/board/*.do와 같은 요청을 모두 이 인터셉트를 거쳐야 한다.
		/resources/**와 같이 css, js, img와 같은 인터넷 자원들은
		인터셉터 적용에서 배재한다. -->
	<interceptors>
	    <interceptor>
	        <mapping path="/board/*.do"/>         
	        <exclude-mapping path="/resources/**"/>
	        <beans:ref bean="loginInterceptor"/>
	    </interceptor>
	</interceptors>


[데이터베이스]

1. 시퀀스 추가
create sequence SEQ_BOARD_NO increment by 1 start with 1;
commit;
	
[자바코드]

1. 스프링 인터셉터 클래스 생성
 - 패키지 생성 : 	com.javalab.interceptor

2. vo 패키지 생성하고 단톡의 클래스를 복사
 - BoardVO, UserVo


3. 매퍼xml 생성
 - src/main/resources 아래에 mapper/oracle 폴더 생성
 - 단톡으로 보낸 매퍼xml을 복사
 

4. 

 1) 특랜젹션 어노테이션 적용
 	AOP가 적용되는 서비스 Layer 클래스 또는 메소드 또는 인터페이스에 
 	@Transactional 어노테이션 적용(일반적으로 서비스Impl 메소드에 적용)
 

5. 인터셉터 작업
 1) 인터셉터 패키지 생성
 2) 인터셉터 클래스 생성
 
 