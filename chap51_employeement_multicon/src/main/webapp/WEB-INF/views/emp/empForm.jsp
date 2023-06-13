<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- 헤더 부분 인클루드 -->
<%@ include file="../include/header.jsp" %>

		<!-- 제목 -->
        <div class="row">
            <div class="col-lg-12">
                <h2 class="page-header" style="text-align: center;">사원 등록</h2>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <!-- 바디(내용) -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        DataTable 3'rd party web grid component
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                    	
                    	<!-- start contents -->
                    	<div>
 							<form id="registerForm" action="<c:url value="/emp/register.do" />" 
 									method="post">
			                    <div class="form-group">
			                        <label for="firstName">First Name</label>
			                        <input type="text" class="form-control" id="firstName" name="firstName" required>
			                    </div>
			                    <div class="form-group">
			                        <label for="lastName">Last Name</label>
			                        <input type="text" class="form-control" id="lastName" name="lastName" required>
			                    </div>
			                    <div class="form-group">
			                        <label for="email">Email</label>
			                        <input type="email" class="form-control" id="email" name="email" required>
			                    </div>
			                    <div class="form-group">
			                        <label for="phoneNumber">Phone Number</label>
			                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" >
			                    </div>
			                    <div class="form-group">
			                        <label for="hireDate">Hire Date</label>
			                        <input type="date" class="form-control" id="hireDate" name="hireDate" required>
			                    </div>
			                    <div class="form-group">
			                        <label for="jobId">jobId(데이터베이스 있는 jobid)</label>
			                        <input type="text" class="form-control" id="jobId" name="jobId" >
			                    </div>
			                    <div class="form-group">
			                        <label for="salary">Salary</label>
			                        <input type="number" class="form-control" id="salary" name="salary" required>
			                    </div>
			                    <div class="form-group">
			                        <label for="departmentId">departmentId(데이터베이스 있는 departmentId)</label>
			                        <input type="text" class="form-control" id="departmentId" name="departmentId" >
			                    </div>
			                    
			                    <div class="form-group">
			                        <input type="submit" id="btnRegister" class='btn btn-primary' value="저장" />
			                        <input type="button" id="btnList" class='btn btn-info' onclick="location.href='<c:url value="/emp/list.do" />'" value="목록" />
			                    </div>
                      
	                        </form>
	                        
						</div>
						
						<!-- 페이지 번호 클릭시 전달될 폼 -->
						<div>
							<form id='actionForm' action="/emp/list.do" method='get'>
								<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
								<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
								<input type='hidden' name='searchText'	value='<c:out value="${ pageMaker.cri.searchText}"/>'>
							</form>		
						</div>
                    </div>
                    <!-- panel-body -->
                </div>
                <!-- panel -->
            </div>
            <!-- col-lg-12 -->
        </div>

	<script type="text/javascript">
		$(document).ready(function() {
			
			var actionForm = $("#actionForm");
			
			// [1] 게시물 제목 클릭 이벤트 처리기(리스너, 핸들러)
			// - 게시물 제목에 href=${board.no}를 걸어놓음. 그리고 그 값을 no라는 name 태그로 서버에 전송
			$(".move").on("click", function(e) {
				e.preventDefault();	// 안막으면 원래 걸려있는 링크로 submit()이 되버림.(a href가 제 기능하지 않도록 방지)
				//console.log('게시물 클릭');
				
				alert($(this).attr("href"));
				
				actionForm.append("<input type='hidden' name='employeeId' value='"
					+ $(this).attr("href")
					+ "'>");
				actionForm.attr("action", "${pageContext.request.contextPath}/emp/read.do");
				actionForm.submit();
			}); // end of move
			
			// [2] 페이지 번호 클릭 이벤트 처리기(리스너, 핸들러)
			// - 페이지 버튼 클릭시 폼태그에 해당 페이지 번호를 세팅해서 서버로 전달(submit)
			$(".paginate_button a").on("click",	function(e) {
				//alert('페이지 번호 클릭');
				e.preventDefault();	// 안막으면 원래 걸려있는 링크로 submit()이 되버림.(a href가 제 기능하지 않도록 방지)
				actionForm.find("input[name='pageNum']").val($(this).attr("href"));
				actionForm.submit();
			});

	         // [3] 검색 버튼 클릭 이벤트 핸들러(리스너)
	        $('#btnSearch').on('click', function(){

	        	// 검색 키워드 널 체크
	 			let searchText = $('#searchText').val();	// let은 var과 동일한 기능, 변수명 중복 체크해주는 장점.
	 			if(searchText == "" || searchText == null || searchText == undefined || searchText.length < 1 ){
	 				alert('검색어를 입력하세요.');
	 				$("#searchText").focus();
	 				return false;
	 			}
				$('#searchForm').submit();					
			});  
			
		}); // end of ready()
		
		
	</script>

 
<!-- 푸터 부분 인클루드 -->
<%@ include file="../include/footer.jsp" %>