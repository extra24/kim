<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index.jsp</title>
</head>
<body>
<h1>회원 게시판 만들기</h1>
<c:if test="${! empty authUser.name }">
${authUser.name }님, 안녕하세요 <a href="logout">[로그아웃하기]</a><a href="changePwd">[비밀번호 변경]</a>
</c:if>
<c:if test="${empty authUser.name }">
${authUser.name }님, 안녕하세요 <a href="join">[회원가입하기]</a> <a href="login">[로그인하기]</a>
</c:if>
</body>
</html>