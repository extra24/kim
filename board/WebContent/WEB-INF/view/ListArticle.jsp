<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ListArticle.jsp</title>
</head>
<body>
<h1>게시글 목록</h1>
<table border="1">
<tr>
	<td colspan="4"><a href="write">[게시판쓰기]</a></td>
</tr>
<tr>
	<th>번호</th>
	<th>제목</th>
	<th>작성자</th>
	<th>조회수</th>
</tr>

<!-- 게시글이 없을 때 보여줄 화면 -->
<c:if test="${!articlePage.hasArticle() }">
	<tr>
		<td colspan="4">게시글이 없습니다.</td>
	</tr>
</c:if>

<!-- 게시글이 있을 때 보여줄 화면 -->
<c:forEach var="article" items="${articlePage.artList }">
	<tr>
	<td>${article.articleId }</td>
	<td><a href="read?no=${article.articleId }&pageNo=${articlePage.currentPage}"> ${article.title }</a></td>
	<td>${article.writer.writerName }</td>
	<td>${article.readCnt }</td>
	</tr>
</c:forEach>

<!-- 게시글이 있다면 페이지블럭도 표현 -->
<c:if test="${articlePage.hasArticle() }">
	<tr>
		<td colspan="4">
			<c:if test="${articlePage.startPage > 5 }">
				<a href="list?pageNo=${articlePage.startPage-5 }">[이전]</a>
			</c:if>
			<c:forEach var="pNo" begin="${articlePage.startPage}" end="${articlePage.endPage }">
				<a href="list?pageNo=${pNo }">[${pNo}]</a>
			</c:forEach>
			<c:if test="${articlePage.endPage < articlePage.totalPages }">
				<a href="list?pageNo=${articlePage.startPage+5 }">[다음]</a>
			</c:if>
		</td>
	</tr>

</c:if>

</table>
</body>
</html>