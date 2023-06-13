<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko-kr">
<head>
<meta charset="UTF-8">
<title>action</title>
</head>
<body>
param1 : ${param1}<br> <!-- model.addAttribute -->
param2 : ${param2}<br> <!-- request.setAttribute -->
param3 : ${param3}<br> <!-- map -->
param4 : ${param4}<br> <!-- RequestBody  : 한글 처리 잘 안됨 -->
param5 : ${param5.name}<br> <!-- VO(POJO) : member -->
param6 : ${member.name}<br> <!-- VO(POJO) : member -->
</body>
</html>