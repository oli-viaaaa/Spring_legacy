<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="../include/header.jsp"%>


<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">게시물 수정</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
  <div class="col-lg-12">
    <div class="panel panel-default">

      <div class="panel-heading">게시물 수정</div>
      <!-- /.panel-heading -->
      <div class="panel-body">

        <form id="modifyForm" role="form" 
        		action="<c:url value="/board/boardModify.do" />"  
        		method="post">
        		
			<!-- 페이징 + 검색을 위해서 관련  정보를 저장해서 저장시 컨트롤러에게 전달 -->			
			<input type="hidden" name="no" value="<c:out value="${board.no}" />" />
			<input type='hidden' name='pageNum' value='${cri.pageNum}'>
			<input type='hidden' name='amount' value='${cri.amount}'>
			<input type='hidden' name='searchText'	value='<c:out value="${cri.searchText}"/>'>
        		
          <div class="form-group">
            <label>제목</label> 
            <input type="text" class="form-control" name='title' id="title" value="<c:out value="${board.title}" />">
          </div>

          <div class="form-group">
            <label>내용</label>
            <textarea class="form-control" rows="10" name='content' id='content'>
            	<c:out	value="${board.content}" escapeXml="false" />
            </textarea>
            <script>CKEDITOR.replace('content');</script>
          </div>

          <div class="form-group">
            <label>작성자</label> 
            <input class="form-control" name='id' id="id" value="<c:out value="${board.id}" />" readonly>
          </div>
          <button type="submit" class="btn btn-success">저장</button>
          <button data-oper='list' value="목록" class="btn btn-info">목록</button>
        </form>

      </div>
      <!--  end panel-body -->
  </div>
  <!-- end panel -->
</div>
<!-- /.row -->

<script type="text/javascript">
	$(document).ready(function() {
		var operForm = $("#modifyForm"); 
		// 목록
		$("button[data-oper='list']").on("click", function(e){  
			operForm.find("#no").remove();	//  목록으로 가기 때문에 no 불필요	
			operForm.attr("method","get");
			operForm.attr("action","/board/boardList.do");
			operForm.submit();    
		});  
	}); // end ready()
</script>


<%@include file="../include/footer.jsp"%>
