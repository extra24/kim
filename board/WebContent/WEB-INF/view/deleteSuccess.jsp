<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deleteSuccess.jsp</title>
</head>
<body>
<h1>삭제에 성공했습니다.</h1>

<br>
${ctxPath = pageContext.request.contextPath; ' ' }
<a href="${ctxPath}/article/list">[게시글 목록보기]</a>

</body>
</html>