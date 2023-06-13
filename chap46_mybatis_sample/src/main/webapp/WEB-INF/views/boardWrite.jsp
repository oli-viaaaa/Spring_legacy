<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8"></meta>
<title>게시물 등록폼</title>

<script type="text/javascript" src='<c:url value="/resources/ckeditor/ckeditor.js" />'></script>

<style type="text/css">
	* {
		font-size: 12px;
	}
	p {
		width: 600px; 
		text-align: right;
	}
	table{
		border-collapse: collapse; 
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
		var form = document.boardWriteForm;
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
		// CKEDITOR 내용체크 해서 없으면 경고창 보여주고 포커스 이동
        var editorData = CKEDITOR.instances.content.getData().trim();
        if (editorData === '') {
            alert('내용을 입력하세요');
            CKEDITOR.instances.content.focus();
            return false;
        }
        
		return true;
	}
</script>
</head>
<body>
	<form name="boardWriteForm" action="<c:url value="/boardWrite.do" />" 
			method="post" onsubmit="return boardWriteCheck();">
	<table summary="게시판 등록 폼">
		<caption>게시판 등록 폼</caption>
		<colgroup>
			<col width="100" />
			<col width="500" />
		</colgroup>
		<tbody>
			<tr>
				<th align="center">제목</th>
				<td><input type="text" name="title" size="80" maxlength="100" /></td>
			</tr>
			<tr>
				<th align="center">작성자</th>
				<td><input type="text" name="writer" maxlength="20" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea name="content" cols="80" rows="10">
					</textarea>
					<script>CKEDITOR.replace('content');</script>
				</td>
			</tr>
		</tbody>
	</table>
	<p>
		<input type="button" value="목록" onclick="goUrl('<c:url value="/boardList.do" />');" />
		<input type="submit" value="저장하기" />
	</p>
	</form>
</body>
</html>


