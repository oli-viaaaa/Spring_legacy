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
	
	.btn_align {
		width: 600px;
		text-align: right;
	}
	
	table tbody tr th {
		background-color: #05bcac;
	}
	</style>
	
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
	<table border="1" summary="게시판 상세조회">
		<caption>게시물 내용보기</caption>
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
				<td><c:out value="${board.writer}" /></td>
			</tr>
			<tr>
				<th align="center">조회수</th>
				<td><c:out value="${board.hit}" /></td>
			</tr>

			<tr>
				<th align="center">등록 일시</th>
				<td><c:out value="${board.regdate}" /></td>
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
		<input type="button" value="목록" onclick="goUrl('<c:url value="/boardList.do" />');" />
		<input type="button" value="수정" 
			onclick="goUrl('<c:url value="/boardModify.do?no=${board.no}" />');" />
		<input type="button" value="삭제"
			onclick="deleteCheck('<c:url value="/boardDelete.do?no=${board.no}" />');" />
	</p>
</body>
</html>