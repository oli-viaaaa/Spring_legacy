<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-kr">
<head>
<meta charset="UTF-8">
<title>form param transfer</title>
</head>
<body>
	
	<!-- 이 부분에서 action 경로를 action, action2로 변경하면서 각 경로별로 테스트합니다. -->
	<form name="frm" 
		  method="post" 
		  action="${pageContext.request.contextPath}/action2">
		<input type="text" id="name" name="name">
		<input type="submit" value="전송">
	</form>	
</body>
</html>