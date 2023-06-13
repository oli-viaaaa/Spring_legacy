<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="../include/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">게시물 상세보기</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">

			<div class="panel-heading">게시물 상세보기 화면</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="form-group">
					<label>게시물번호</label> 
					<input class="form-control" name='bno' value='<c:out value="${board.no }"/>' readonly="readonly">
				</div>

				<div class="form-group">
					<label>제목</label> 
					<input class="form-control" name='title' value='<c:out value="${board.title }"/>' readonly="readonly">
				</div>

				<div class="form-group">
					<label>내용</label>
					<textarea class="form-control" rows="3" name='content'
						readonly="readonly"><c:out value="${board.content}" /></textarea>
				</div>

				<div class="form-group">
					<label>작성자</label> 
					<input class="form-control" name='id' value=<c:out value="${board.id }"/> readonly="readonly">
				</div>
				
				<button data-oper='list' class="btn btn-success"  value="목록">목록</button>
				<button data-oper='modify' class="btn btn-info" value="수정">수정</button>
				<button data-oper='delete' class="btn btn-warning" value="삭제">삭제</button>				

				<form id='operForm' action="${pageContext.request.contextPath}/board/boardModify.do" method="get">
				  <input type='hidden' id='no' name='no' value='<c:out value="${board.no}"/>'>
				  <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
				  <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
				  <input type='hidden' name='searchText' value='<c:out value="${cri.searchText}"/>'>
				</form>
			</div>
			<!--  end panel-body -->
		</div>
		<!--  end panel-heading -->
	</div>
	<!-- end panel -->
</div>
<!-- /.row -->

<script type="text/javascript">
	$(document).ready(function() {

		var operForm = $("#operForm");		
		
		// 목록(Html5 제공하는 data-* 값을 사용해서 각각 다른 submit())
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
			operForm.attr("action","/board/boardDelete.do");
			operForm.submit();    
		});  
		
	});
</script>


<%@include file="../include/footer.jsp"%>
