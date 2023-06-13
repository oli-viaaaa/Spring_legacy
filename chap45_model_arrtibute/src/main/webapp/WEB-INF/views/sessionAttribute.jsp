<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>session List status</title>
</head>
<body>
	<h3> @SessionAttributes 통해서 session에 저장된 회원정보 </h3>
	<br>
	1. id : ${sessionScope.member.id}<br>
	2. name : ${sessionScope.member.name}<br>
	3. age : ${sessionScope.member.age}<br>
	4. bloodType : ${sessionScope.member.bloodType}<br>
	5. gender : ${sessionScope.member.gender}<br>
	<br><br>
	
	<h3> model에 저장한 회원정보 </h3>
	<br>
	1. id : ${member.id}<br>
	2. name : ${member.name}<br>
	3. age : ${member.age}<br>
	4. bloodType : ${member.bloodType}<br>
	5. gender : ${member.gender}<br>
</body>
</html>