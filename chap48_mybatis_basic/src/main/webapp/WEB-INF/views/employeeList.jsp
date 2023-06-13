<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

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

		table, th, td {
		  padding: 10px;
		  text-align: left;
		}
		
		th, td {
		  border-bottom: 1px solid #ddd;	/* 한줄 선긋기 */
		}
			
		table thead tr th {
			background-color: #05bcac;
			
		}
	</style>
</head>

<body>

	<table>
		<thead>
			 <tr>
				 <th width="50px" align="center">id</th>
				 <th align="center">name</th>
				 <th align="center">email</th>
				 <th align="center">phoneNumber</th>
				 <th align="center">hireDate</th>
				 <th align="center">jobId</th>
				 <th align="center">salary</th>
				 <th align="center">pct</th>
				 <th align="center">managerId</th>
				 <th align="center">departmentId</th>
			 </tr>
		 </thead>
		<tbody>
			 <c:forEach items="${employeeList}" var="employees">
				 <tr>
					 <td>${employees.employeeId}</td>
					 <td>
					 	<a href="${pageContext.request.contextPath}/emp/read?employeeId=${employees.employeeId}">
					 		${employees.firstName} ${employees.lastName}
					 	</a>
					 </td>
					 <td>${employees.email}</td>
					 <td>${employees.phoneNumber}</td>
					 <td>${employees.hireDate}</td>
					 <td>${employees.jobId}</td>
					 <td>${employees.salary}</td>
					 <td>${employees.commissionPct}</td>
					 <td>${employees.managerName }</td>
					 <td>${employees.departmentName }</td>	
				 </tr>
			 </c:forEach>
		</tbody>
	</table>
	<br>
	<a href="${pageContext.request.contextPath}/emp/form">
		<button>사원등록</button>
	</a>

</body>
</html>
