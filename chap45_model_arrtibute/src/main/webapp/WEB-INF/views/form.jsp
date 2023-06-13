
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- [스프링 폼태그 라이브러리]
   스프링 프레임워크의 JSP 기술중에 form taglib 가 있다. 
   form 태그라이브러리를 사용하면 HTML 폼에 컨트롤러에서 보낸 
   데이터를 편리하게 바인딩할 수 있습니다. -->   
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Form</title>
</head>
<body>
   <h1>스프링 Form 태그 예제</h1>
   <!-- [modelAttribute]
      폼에 있는 요소들의 값을 채우기 위해서 컨트롤러에서 모델에 저장해놓은
      객체를 찾을 때 쓰는 이름이다.
      컨트롤러에서 저장한 형태 model.addAttribute("member2", member) 
      
      modelAttribute 생성 하면 path로 path="id" 라고 하면 name="id"와 같은 효과 낼 수 있다.-->
   <form:form modelAttribute="member2" action="/action" method="post">
      
      <label for="id">ID:</label>
      <!-- path="id" 라고 하면 name="id"와 같은 효과 -->
      <form:input path="id" readonly="true" />
      <br>

      <label for="name">Name:</label>
      <form:input path="name" />
      <br>

      <label for="age">Age:</label>
      <!-- path="age" 라고 하면 name="age"와 같은 효과 -->
      <form:input path="age" />
      <br>
      
      <label for="bloodType">Blood Type:</label>
      <!-- 컨트롤러 : @ModelAttribute("modelVo")
               modelVo.put("bloodType", bloodType); -->
      <form:select path="bloodType" items="${modelVo.bloodType}" />
      <br>

      <label for="gender">Gender:</label>
      <!-- 컨트롤러 : @ModelAttribute("modelVo")
               modelVo.put("gender", gender); -->
      <form:select path="gender" items="${modelVo.gender}" />
      <br>

      <input type="submit" value="Submit" />

   </form:form>

</body>
</html>