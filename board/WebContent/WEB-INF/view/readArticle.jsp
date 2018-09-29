<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--내가 지정한 태그파일이 있는 폴더를 지정하고 사용자 태그를 사용할 수 있다. --%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>readArticle.jsp</title>
</head>
<body>
<h1>게시글 내용을 보여주는 페이지 화면</h1>

<table border="1">
	<tr>
	<th>게시글 번호</th>
	<th>작성자이름</th>
	<th>제목</th>
	<th>내용</th>
	</tr>
	<tr>
	
		<td>${articleData.article.articleId }</td>
		<td>${articleData.article.writer.writerName }</td>		
		<td>${articleData.article.title }</td>
		
		<%--u는 접두어 pre는 파일명이름 value는 태그에 설정한 value값이다.--%>
		<td><u:pre value='${articleData.content}'/></td>
	
	</tr>
	<tr>
	<td colspan="4">
	<c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo }" />
	<a href="list?pageNo=${pageNo} ">[목록]</a>
	<c:if test="${authUser.userId ==articleData.article.writer.writerId }">
		<a href="modify?no=${articleData.article.articleId }">[게시글수정]</a>
		<a href="delete?no=${articleData.article.articleId }">[게시글삭제]</a>
	</c:if>
	</td>
	</tr>
</table>
</body>
</html>