<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>

  	<style type="text/css">
		.backColor{
			background-color: lightBlue;
		}
		#login_error{
			color : red;
			text-align: center;
		}
	</style>	

	<!-- jQuery CDN 포털사이트에서 제공하는 jquery 함수 사용 -->
	<script  src="http://code.jquery.com/jquery-latest.min.js"></script> 
	

	<script type="text/javascript">
		$(document).ready(function(){
			//body 에 배경색 바꾸는 방법 
			$('body').addClass('backColor');
	
		  	$('#btnLogin').on('click', function(){
		  		let _id = $('#id').val();	// id 입력값
		  		let _pwd = $('#pwd').val();	// pwd 입력값
		  		
		  		$('#loginError').empty(); // 오류 메시지 Html 자식 tag 삭제
		  		
		  		if(_id == null || _id.length == 0){
		  			alert('ID를 입력하세요.');
		  			$('#id').focus();
		  			return false;
		  		}
		  		if(_pwd == null || _pwd.length == 0){
		  			alert('비밀번호를 입력하세요.');
		  			$('#pwd').focus();
		  			return false;
		  		}
		  		$('#frmLogin').submit();
		  	});	// end on change event
		});	
		
		// 자바스크립트에서 컨텍스트 경로 가져오는 방법
		function getContextPath() {
		    return window.location.pathname
		    		.substring(0, window.location.pathname.indexOf("/",2));
		}
	</script>

</head>
<body>
	<form name="frmLogin" id="frmLogin" method="post" action="${contextPath}/login/login.do">
		<h1 style="text-align: center">로그인</h1>
		<table align="center" border="0">
			<tr>
				<td width="200"><p align="right">아이디</td>
				<td width="400"><input type="text" name="id" id="id" value="${userVo.id}"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">비밀번호</td>
				<td width="400"><input type="password" name="pwd" id="pwd" value="${userVo.pwd}"></td>
			</tr>
			<c:if test="${loginErrMsg}">
				<tr id="loginError">
					<td colspan="2">
						<p id="login_error">아이디와 비밀번호가 맞지 않습니다.</p>
					</td>
				</tr>
			</c:if>			
			<tr>
				<td width="200"><p>&nbsp;</p></td>
				<td width="400">
					<input type="button" id="btnLogin" value="로그인">
					<input	type="reset" value="다시입력">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>