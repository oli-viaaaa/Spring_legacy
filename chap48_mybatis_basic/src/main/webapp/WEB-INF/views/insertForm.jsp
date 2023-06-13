<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html lang="ko">
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
	</script>
</head>
<body>
	<h1 style="text-align:center;">사원등록</h1>

	<div>
		<form:form modelAttribute="employees"
				   method="POST"
		  		   action="${pageContext.request.contextPath}/emp/insert">
			<table>
				<colgroup>
					<col width="10%" />
					<col width="90%" />
				</colgroup>
				<tbody>
					<tr>
						<th align="center">firstName</th>
						<td>
							<form:input path="firstName" maxlength="20" size="25" />
						</td>
					</tr>
					<tr>
						<th align="center">lastName</th>
						<td>
							<form:input path="lastName" maxlength="20" size="25" />
						</td>
					</tr>
					<tr>
						<th align="center">email</th>
						<td>
							<form:input path="email" maxlength="20" size="25" />
						</td>
					</tr>
					<tr>
						<th align="center">phoneNumber</th>
						<td>
							<form:input path="phoneNumber" maxlength="20" size="25" />
						</td>
					</tr>			
					<tr>
						<th align="center">hireDate</th>
						<td>
							<form:input type="date" path="hireDate" value="${empVo.hireDate}" maxlength="20" size="25" />
						</td>
					</tr>			
					<tr>
						<th align="center">jobId</th>
						<td>
							<form:input path="jobId" maxlength="20" size="25" />
						</td>
					</tr>
					<tr>
						<th align="center">salary</th>
						<td>
							<form:input type="number" path="salary" />
						</td>
					</tr>	
					<tr>
						<th align="center">commissionPct</th>
						<td>
							<form:input path="commissionPct" maxlength="20" size="25" />
						</td>
					</tr>					
					<tr>
						<th align="center">managerId</th>
						<td>
							<form:input path="managerId" maxlength="20" size="25" />
						</td>
					</tr>					
					<tr>
						<th align="center">departmentId</th>
						<td>
							<form:select path="departmentId" items="${empVo.departmentList}" 
								itemValue="departmentId" itemLabel="departmentName" />
						</td>
					</tr>					
				</tbody>
			</table>
			
			<p class="btn_align">
				<input type="submit" value="저장" />
				<input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath}/emp/list'" />
			</p>
		</form:form>	
	</div>	
</body>
</html>


