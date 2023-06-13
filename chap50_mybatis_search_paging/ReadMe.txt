[프로젝트 특이사항]

 1. 스프링 + 마이바티스 
 2. boardList.jsp에 부트스트랩 import해야  함.
 3. 검색 + 페이징
 
[프로젝트 생성]
 - chap49_transactional 프로젝트와 유사함. 
 
[주요기술]

 1. 
 
[진행단계]

1. 페이징 전용 클래스(Criteria) 생성 : 요청페이지, 한페이지에 보여줄갯수, 검색어 저장용 클래스
	public class Criteria {
		private int pageNum = 1;	// 요청 페이지 번호
		private int amount = 10; 		// 한페이지에 보여줄 게시물수
		private String searchText = "" ; 	// 검색 키워드
		
2. PageDto 생성 : Criteria + 한 페이지에 보여줄 게시물에 대한 정보 저장 클래스(시작게시물, 끝게시물 등) 
	@Getter
	@ToString
	public class PageDto {
	
		private int startPage;
		private int endPage;
		private boolean prev, next;
	
		private int total;
		private Criteria cri;

3. 데이터베이스에서 다음 쿼리로 인덱스를 이용한 테스트 성공 후에 자바 코딩
 
  (1) 단위테스트 전에 게시물을 300개 정도 등록해놓을것.
  	insert into simple_board(no, title, content, id, hit, regdate)
	(select seq_board.nextval, title, content, id, hit, regdate from board);
  
  (2) 인덱스를 통한 쿼리 확인 
	  select 
	    no, title, content, id, hit, regdate
	  from 
	      (
	      select /*+INDEX_DESC(simple_board SIMPLE_BOARD_PK) */
	        rownum rn, no, title, content, id, hit, regdate 
	      from 
	        simple_board
	      where rownum <= 20
	      )
	      where rn > 10
	      
	       
4. com.javalab.dao 패키지의 BoardDAO 매퍼 인터페이스에 다음 메소드 추가

 1) List<BoardVO> getListPagingAndSearching(Criteria cri);	// 글 목록 조회(Criteria 객체 사용)

5. boardMapper.xml

 3) boardMapper.xml에 다음 메소드 추가(기본적으로 2번째 페이지 출력하도록 하드코딩
    - 테스트 성공 후에 코드 수정해야 함. 나중에 알고리즘 확정되면 인자받아서 처리하도록 수정)
 
 	<!-- 게시물 목록 조회(페이징 기능 추가) -->
	<select id="getListPaging"	resultType="Board">
	  <![CDATA[
	  select 
	    no, title, content, id, hit, regdate
	  from 
	      (
	      select /*+INDEX_DESC(simple_board SIMPLE_BOARD_PK) */
	        rownum rn, no, title, content, id, hit, regdate 
	      from 
	        simple_board
	      where rownum <= 20
	      )
	      where rn > 10     
  	]]>
	</select>
 
 3) [매퍼 인터페이스 단위테스트]
 	@Test
	public void testBoardPaging() {
		Criteria cri = new Criteria();
		List<BoardVO> list = dao.getListPagingAndSearching(cri);
		log.info("list.size(): " + list.size());
	}
  
	
 4) 단위테스트 성공시 boardMapper.xml에서 getListPagingAndSearching() 수정할것.
 
3. getBoardList() 수정 - [주의] (#{pageNum} - 1) 오타 경고해줄것.

	<select id="getListPagingAndSearching" parameterType="Criteria" resultType="Board">
		.......... 중략 ........
      where rownum <= #{pageNum} * #{amount}
	      )
	      where rn > (#{pageNum} - 1) * #{amount} 

4. 단위테스트 실행, 첫번째 페이지 조회
	@Test
	public void testPaging() {
		Criteria cri = new Criteria(2, 10);	// 2번째 페이지 조회
		List<BoardVO> list = dao.getListPagingAndSearching(cri);
		list.forEach(board -> log.info(board.toString()));
	}

----------------- Dao (매퍼인터페이스 + 매퍼XML) 테스트 완료 ---------------

3. BoardService 인터페이스와 서비스 Impl에 다음 메소드 추가
 1) 인터페이스
 	List<BoardVO> getListPagingAndSearching(Criteria cri);	// 글 목록 조회(페이징)
 
 2) Impl 
 	// 페이징 처리를 위한 게시물 목록 조회
	@Override
	public List<BoardVO> getListPaging(Criteria cri) {
		log.info(cri.toString());
		List<BoardVO> boardList = boardDAO.getListPagingAndSearching(cri);
		return boardList;
	}	
	
4. 서비스단 단위테스트, 다음 메소드 추가
	@Test
	public void testPagingService() {
		Criteria cri = new Criteria(1, 10);
		List<BoardVO> list = service.getListPaging(cri);
		list.forEach(board -> {log.info(board.toString());});
	}

5. BoardController 컨트롤러 코드 수정
 
 1) 기존 getBoardList() 메소드 주석처리
 
 2) 페이징 기능이 있는 새로운 메소드 추가
 	// 페이징 기능 있는 게시물 리스트 조회 핸들러
	@GetMapping("/boardList.do")
	public String getListPaging(Criteria cri, Model model){
		log.info("selectBoardList 메소드 Criteria : " + cri);
		List<BoardVO> boardList = boardService.getListPaging(cri);
		model.addAttribute("boardList", boardList);
		return "/board/boardList";	// jsp 페이지
	}

 3) 다음 메소드 확인(전체 게시물 조회 메소드)
  
 	// 전체 게시물 또는 조회 조건에 맞는 전체 게시물 건수
	@GetMapping("/boardCount.do")
	public int getTotalBoardCount(Criteria cri, Model model){
		log.info("getTotalBoardCount 메소드");
		int count = boardService.getTotalBoardCount(cri);
		return count;
	}


6. HomeController 에서 다음 사항 수정
 1) return "redirect:/board/boardList.do"; 게시물 목록으로 이동
 
7. 인터셉터에서 다음 부분 수정해서 servlet-context.xml에 적용
 - boardList.do는 인터셉터 제외
 
  <!-- 인터셉터 객체 빈생성 -->
	<beans:bean id="loginInterceptor" class="com.javalab.interceptor.LoginInterceptor">
	</beans:bean>
	<!-- Interceptor 설정 -->
	<interceptors>
	    <interceptor>
	        <mapping path="/board/*.do"/>
	        <exclude-mapping path="/resources/**"/>
	        <!-- 인터셉터에서 임시적으로 제외 -->
	        <exclude-mapping path="/board/boardList.do"/>
	        <beans:ref bean="loginInterceptor"/>
	    </interceptor>
	</interceptors>
	
 
[화면 작업] 
 
1. 부트스트랩 css/js, jquery를 import
 
 
[프로젝트 종료후 핵심 개념 설명]

1. 오라클 인덱스
2. 마이바티스 조건절
3. jQuery
 
=======================================

insert into simple_board(no, title, content, id, hit, regdate) values('11', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('12', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('13', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('14', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('15', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('16', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('17', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('18', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('19', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('20', 'title', 'content', '1', 0, sysdate);

insert into simple_board(no, title, content, id, hit, regdate) values('21', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('22', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('23', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('24', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('25', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('26', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('27', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('28', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('29', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('30', 'title', 'content', '1', 0, sysdate);

insert into simple_board(no, title, content, id, hit, regdate) values('31', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('32', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('33', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('34', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('35', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('36', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('37', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('38', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('39', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('40', 'title', 'content', '1', 0, sysdate);

insert into simple_board(no, title, content, id, hit, regdate) values('41', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('42', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('43', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('44', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('45', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('46', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('47', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('48', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('49', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('50', 'title', 'content', '1', 0, sysdate);
    
insert into simple_board(no, title, content, id, hit, regdate) values('51', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('52', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('53', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('54', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('55', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('56', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('57', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('58', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('59', 'title', 'content', '1', 0, sysdate);
insert into simple_board(no, title, content, id, hit, regdate) values('60', 'title', 'content', '1', 0, sysdate);
    
commit; 

----------------------------------
2022.09.27 추가 작업 : 수정 삭제 조회

1. BoardController 수정 getBoardById 메소드 수정 (List 부분)
  - @ModelAttribute("cri") Criteria cri 추가
  
 2) boardView.jsp
  (1) jQuery import
	<!-- jQuery -->
	<!-- <script  src="http://code.jquery.com/jquery-latest.min.js"></script> -->
    <!-- jQuery  -->
	<script  src='<c:url value="/resources/jquery/jquery.min.js" />'></script>

		
  (2) <p class="btn_align"> 부분 수정
		<%--<input type="button" data-oper='list' value="목록" onclick="fnClickBtn(this)" />
		<input type="button" data-oper='modify' value="수정" onclick="goUrl('<c:url value="/board/boardModify.do?no=${board.no}" />');" />
		<input type="button" data-oper='delete' value="삭제" onclick="deleteCheck('<c:url value="/board/boardDelete.do?no=${board.no}" />');" />
		 --%>
		 
		<button data-oper='list' value="목록">목록</button>
		<button data-oper='modify' value="수정">수정</button>
		<button data-oper='delete' value="삭제">삭제</button>
	</p>	
  (3) Html 폼 수정
		<form id='operForm' action="${pageContext.request.contextPath}/board/boardModify.do" method="get">
		  <input type='hidden' id='no' name='no' value='<c:out value="${board.no}"/>'>
		  <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
		  <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
		  <input type='hidden' name='searchText' value='<c:out value="${cri.searchText}"/>'>
		</form>
	
  (4) 자바스크립트 수정	 
	 <script type="text/javascript">
		$(document).ready(function() {
			  
			var operForm = $("#operForm");		
			// 목록
			$("button[data-oper='list']").on("click", function(e){  
				operForm.find("#no").remove();	//  목록으로 가기 때문에 no 불필요	
				operForm.attr("action","/board/boardList.do");
				operForm.submit();    
			});  
	
			// 수정
			$("button[data-oper='modify']").on("click", function(e){    
				operForm.attr("action","/board/boardModify.do");
				operForm.submit();    
			});  
	
			// 삭제
			$("button[data-oper='delete']").on("click", function(e){    
				operForm.attr("action","/board/delete.do");
				operForm.submit();    
			});  
		  
		}); // end ready()
	</script>
 
   (5) boardView 테스트 하고 설명.
 
2. updateBoardForm() get() 메소드 수정(수정부분)
  (1) BoardController 수정
   - @ModelAttribute("cri") Criteria cri,
  
  (2) 화면부분 jQuery 추가
  	<!-- jQuery -->
	<script  src='<c:url value="/resources/jquery/jquery.min.js" />'></script>
    
  (3) 화면에 Html 수정(data-* 사용)
    <button data-oper='list' value="목록">목록</button>
    
  (4) 자바스크립트 수정   
	// 목록
	$("button[data-oper='list']").on("click", function(e){  
		operForm.find("#no").remove();	//  목록으로 가기 때문에 no 불필요	
		operForm.attr("method","get");
		operForm.attr("action","/board/boardList.do");
		operForm.submit();    
	});  
  
  (3) boardModify() Post 메소드에 파라미터 추가

  - @ModelAttribute("cri") Criteria cri 

		// redirect 하기 때문에 다음과 같이 넣어줘야 함.
		// rttr.addAttribute는 GET 방식이며 페이지를 새로고침 한다 해도 값이 유지된다.
		// rttr.addFlashAttribute는 POST 방식이며 이름처럼 일회성 데이터라 새로고침 하면 값이 사라진다.
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("searchText", cri.getSearchText());
	
  - RedirectAttributes 설명	
  
3. 컨트롤러의 boardDelete() 수정
   
  
[개념 설명]
  
1. data-* 사용법

2. e.preventDefault(); 역할

3. jsp에서 submit()으로 전달된 값들이 커맨드 객체에 세팅되는 매커니즘
 - submit()이 안되면 Criteria 기본 객체가 생성되면서 페이지가 1이 되버림. 

4. RedirectAttributes 설명
		