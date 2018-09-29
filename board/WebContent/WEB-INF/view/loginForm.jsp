<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginForm.jsp</title>
</head>
<body>
	<h1>로그인</h1>
	<form action="login" method="post">
	<c:if test="${errors.IdOrPwdNotMatch }">아이디와 비밀번호가 맞지 않습니다.</c:if>
		<p>
			<input type="text" name="loginId" value="${param.loginId }" placeholder="아이디"><br>
			<c:if test="${errors.loginId }">아이디를 입력하세요</c:if>
		</p>
		<p>
			<input type="password" name="password" value="${param.password }" placeholder="비밀번호"><br>
			<c:if test="${errors.password }">비밀번호를 입력하세요</c:if>
		</p>
	<input type="submit" value="로그인">
	</form>
</body>
</html>