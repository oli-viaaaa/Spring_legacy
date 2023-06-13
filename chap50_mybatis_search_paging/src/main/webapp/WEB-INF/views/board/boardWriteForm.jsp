<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8"></meta>
<title>게시물 작성</title>

	<!-- ckeditor용 자바 스크립트 import -->
	<script type="text/javascript" src='<c:url value="/resources/ckeditor/ckeditor.js" />'></script>
	
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
	
	<script type="text/javascript">
		function goUrl(url) {
			location.href=url;
		}
		// 등록 폼 체크
		function boardWriteCheck() {
			var form = document.boardForm;
			if (form.title.value == '') {
				alert('제목을 입력하세요.');
				form.title.focus();
				return false;
			}
			if (form.writer.value == '') {
				alert('작성자를 입력하세요');
				form.writer.focus();
				return false;
			}
			return true;
		}
	</script>
</head>
<body>
	<h1 style="text-align:center;">게시물 작성</h1>
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
		<form name="boardForm" action="<c:url value="/board/boardWrite.do" />" 
				method="post" 
				enctype="multipart/form-data">
			<table>
				<colgroup>
					<col width="10%" />
					<col width="90%" />
				</colgroup>
				<tbody>
					<tr>
						<th align="center">제목</th>
						<td><input type="text" name="title" size="80" maxlength="100" /></td>
					</tr>
					<tr>
						<th align="center">작성자</th>
						<td>
							<input type="text" name="id" maxlength="20" value="${user.id }" readonly />
						</td>
					</tr>
					<tr>
						<th align="center">내용</th>
						<td>
							<textarea name="content" cols="500" rows="10">
							</textarea>
							<script>CKEDITOR.replace('content');</script>
						</td>
					</tr>
					<!-- file upload -->
					<tr>
						<th align="center">파일 업로드</th>
						<td align="left"><input type="file" name="uploadFile"></td>
					</tr>					
							
				</tbody>
			</table>
			<p class="btn_align">
				<input type="submit" value="저장" />
				<input type="button" value="목록" onclick="goUrl('<c:url value="/board/boardList.do" />');" />
			</p>
		</form>
	</div>	
</body>
</html>


