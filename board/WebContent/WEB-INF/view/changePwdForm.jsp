<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>changePwdForm.jsp</title>
</head>
<body>
	<h1>비밀번호 변경</h1>
	<form action="changePwd" method="post">
	<c:if test="${errors.samePwd }">바꾸려는 비밀번호가 현재 비밀번호와 같습니다.</c:if>
		<p>
			<input type="password" name="oldPwd" placeholder="현재 비밀번호">
			<c:if test="${errors.oldPwd }">현재 비밀번호를 입력해 주세요.</c:if>
			<c:if test="${errors.wrongOldPwd }">잘못된 비밀번호 입니다.</c:if>
		</p>
		<p>
			<input type="password" name="newPwd" placeholder="새로운 비밀번호">
			<c:if test="${errors.newPwd }">새로운 비밀번호를 입력해 주세요.</c:if>
		</p>
		<input type="submit" value="비밀번호 변경">
	</form>
</body>
</html>