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
	<h1 style="text-align:center;">사원 정보 보기</h1>
	<div>
		<table>
			<colgroup>
				<col width="100" />
				<col width="500" />
			</colgroup>
			<tbody>
				<tr>
					<th align="center">사원ID</th>
					<td><c:out value="${employees.employeeId }" escapeXml="false"/></td>
				</tr>
				<tr>
					<th align="center">성명</th>
					<td><c:out value="${employees.firstName} ${employees.lastName}" /></td>
				</tr>
				<tr>
					<th align="center">이메일</th>
					<td><c:out value="${employees.email}" /></td>
				</tr>
				<tr>
					<th align="center">연락처</th>
					<td><c:out value="${employees.phoneNumber}" /></td>
				</tr>
				<tr>
					<th align="center">입사일</th>
					<td><c:out value="${employees.hireDate}" /></td>
				</tr>
				<tr>
					<th align="center">급여</th>
					<td>
						<c:out value="${employees.salary}" />
					</td>
				</tr>
			</tbody>
		</table>
		<p class="btn_align">
			<input type="button" value="목록" style='cursor:pointer;' onclick="goUrl('<c:url value="/emp/list" />');" />
			<input type="button" value="수정[미적용]" style='cursor:pointer;'  onclick="goUrl('<c:url value="/emp/modify?no=${employees.employeeId}" />');" />
			<input type="button" value="삭제[미적용]" style='cursor:pointer;'  onclick="deleteCheck('<c:url value="/emp/delete.do?no=${employees.employeeId}" />');" />
		</p>
	</div>	
</body>
</html>