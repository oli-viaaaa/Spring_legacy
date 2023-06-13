<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>게시물 내용보기</title>

	<style type="text/css">
		* {
			font-size: 12px;
		}
		table{
			width: 100%;
			border: 1px solid;
			border-collapse: collapse; */  /* 테이블 border 한줄로 나오게 */
			padding : 5px; 
			line-height: 20px;
		}	
		.btn_align {
			width: 100%;
			text-align: center;
		}
		table, th, td {
		  padding: 10px;
		  text-align: left;
		}
		th, td {
		  border-bottom: 1px solid #ddd;
		}
			
		table tbody tr th {
			background-color: #05bcac;
		}
	</style>
	
    <!-- jQuery  -->
	<script  src='<c:url value="/resources/jquery/jquery.min.js" />'></script>

	
	<script type="text/javascript">
		function goUrl(url) {
			location.href = url;
		}
		// 삭제 체크
		function deleteCheck(url) {
			if (confirm('정말 삭제하시겠어요?')) {
				location.href = url;
			}
		}
	</script>
</head>
<body>
	<h1 style="text-align:center;">게시물 내용보기</h1>
	<div style="text-align: right;">
		<c:if test="${not empty user.id}" >
			<strong>${user.name}(${user.id})님</strong>
			<a href="${pageContext.request.contextPath}/login/logout.do"><strong>로그아웃</strong></a>
		</c:if>
		<c:if test="${empty user.id}" >
			<a href="${pageContext.request.contextPath}/login/login.do"><strong>로그인</strong></a>
		</c:if>
	</div>
	<div>
		<table>
			<colgroup>
				<col width="100" />
				<col width="500" />
			</colgroup>
			<tbody>
				<tr>
					<th align="center">제목</th>
					<td><c:out value="${board.title}" escapeXml="false"/></td>
				</tr>
				<tr>
					<th align="center">작성자</th>
					<td><c:out value="${board.id}" /></td>
				</tr>
				<tr>
					<th align="center">조회수</th>
					<td><c:out value="${board.hit}" /></td>
				</tr>
	
				<tr>
					<th align="center">등록 일시</th>
					<td><c:out value="${board.regDate}" /></td>
				</tr>
				<tr>
					<th align="center">내용</th>
					<td>
						<c:out value="${board.content}" escapeXml="false" />
					</td>
				</tr>
			</tbody>
		</table>
		<p class="btn_align">
		<%--<input type="button" data-oper='list' value="목록" onclick="fnClickBtn(this)" />
			<input type="button" data-oper='modify' value="수정" onclick="goUrl('<c:url value="/board/boardModify.do?no=${board.no}" />');" />
			<input type="button" data-oper='delete' value="삭제" onclick="deleteCheck('<c:url value="/board/boardDelete.do?no=${board.no}" />');" />
		--%>
			 
			<button data-oper='list' value="목록">목록</button>
			<button data-oper='modify' value="수정">수정</button>
			<button data-oper='delete' value="삭제">삭제</button>
		</p>
		
		<form id='operForm' action="${pageContext.request.contextPath}/board/boardModify.do" method="get">
		  <input type='hidden' id='no' name='no' value='<c:out value="${board.no}"/>'>
		  <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
		  <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
		  <input type='hidden' name='searchText' value='<c:out value="${cri.searchText}"/>'>
		</form>
	</div>


<script type="text/javascript">
	$(document).ready(function() {
		  
		var operForm = $("#operForm");		
		
		// 목록(Html5 제공하는 data-* 값을 사용해서 각각 다른 submit())
		$("button[data-oper='list']").on("click", function(e){  
			operForm.find("#no").remove();	//  목록으로 가기 때문에 no 불필요	
			operForm.attr("action","${pageContext.request.contextPath}/board/boardList.do");
			operForm.submit();    
		});  

		// 수정
		$("button[data-oper='modify']").on("click", function(e){    
			operForm.attr("action","${pageContext.request.contextPath}/board/boardModify.do");
			operForm.submit();    
		});  

		// 삭제
		$("button[data-oper='delete']").on("click", function(e){  
			operForm.attr("action","${pageContext.request.contextPath}/board/boardDelete.do");
			operForm.submit();    
		});  
	  
	}); // end ready()
</script>

</body>
</html>