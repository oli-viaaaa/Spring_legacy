<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="../include/header.jsp"%>


<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">게시물 등록</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
  <div class="col-lg-12">
    <div class="panel panel-default">

      <div class="panel-heading">게시물 등록</div>
      <!-- /.panel-heading -->
      <div class="panel-body">

        <form role="form" action="<c:url value="/board/boardWrite.do" />"  method="post">
          <div class="form-group">
            <label>제목</label> 
            <input type="text" class="form-control" name='title' id="title">
          </div>

          <div class="form-group">
            <label>내용</label>
            <textarea class="form-control" rows="10" name='content' id="content"></textarea>
            <script>CKEDITOR.replace('content');</script>
          </div>

          <div class="form-group">
            <label>작성자</label> 
            <input class="form-control" name='id' id="id" value="${user.id }" readonly>
          </div>
          <button type="submit" class="btn btn-success">저장</button>
          <button type="reset" class="btn btn-info">다시쓰기</button>
        </form>

      </div>
      <!--  end panel-body -->

    </div>
    <!--  end panel-body -->
  </div>
  <!-- end panel -->
</div>
<!-- /.row -->


<script>
	//$(document).ready(function(e){	
	//});
</script>


<%@include file="../include/footer.jsp"%>
