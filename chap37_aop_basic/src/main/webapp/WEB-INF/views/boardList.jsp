<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
	<script type="text/javascript">
		function goUrl(url) {
			location.href = url;
		}	
	</script>
	<style type="text/css">
		* {
			font-size: 12px;
		}
		a {
		  text-decoration: none;	/* a tag 태그 밑줄 제거 */
		}		
		p {
			width: 600px;
			text-align: right;
		}
		table{
			/* border-collapse: collapse; */  /* 테이블 border 한줄로 나오게 */ 
		}
		table thead tr th {
			background-color: #05bcac;
		}
	</style>
</head>
<body>
	<table border="1" summary="게시판 목록">
		<caption><b>게시물 목록</b></caption>
		<colgroup>
			<col width="50" />
			<col width="300" />
			<col width="80" />
			<col width="100" />
			<col width="70" />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>등록 일시</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${boardList.size() <= 0}">
					<tr>
						<td align="center" colspan="5">등록된 게시물이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="board" items="${boardList}" varStatus="i">
						<tr>
							<td align="center">
								<c:out value="${i.count}" />
							</td>
							<td>
								<a href="<c:url value='/boardView.do?no=${board.no}' />">
									<c:out value="${board.title}" />
								</a>
							</td>
							<td align="center">
								<c:out value="${board.writer}" />
							</td>
							<td align="center">
								<c:out value="${board.regdate}" />
							</td>
							<td align="center">
								<c:out value="${board.hit}" />
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<br>
	<input type="button" value="게시물 작성" onclick="goUrl('<c:url value="/boardWrite.do" />');" />
</body>
</html>