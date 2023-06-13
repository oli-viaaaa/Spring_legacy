<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환영</title>

</head>
<body>
	${sessionScope.user.id}(님) ${sessionScope.user.roleId} 모드로 로그인 하셨습니다.
</body>
</html>