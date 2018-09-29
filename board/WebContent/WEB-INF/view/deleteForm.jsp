<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deleteForm.jsp</title>
</head>
<body>
<h1>게시글 삭제</h1>
<form action="delete" method="post">
	<input type="hidden" name="no" value="${delReq.articleId }">
	<p>
	게시글 번호 ${delReq.articleId }를 정말 삭제하시겠습니까?
	</p>
	<input type="submit" value="글 삭제">
</form>
</body>
</html>