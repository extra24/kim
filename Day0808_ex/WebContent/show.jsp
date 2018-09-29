<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insert title here</title>
</head>
<body>
<%
	request.setCharacterEncoding("utf-8");
	String anim = request.getParameter("ani");
	String pram = null;
	
	switch(anim){
	case "dog" : 
	pram = "dog.jsp";
	break;
	case "cat": 
	pram = "cat.jsp";
	break;
	case "fish": 
	pram = "fish.jsp";
	break;	
	}
	
%>

<jsp:forward page="<%=pram %>"></jsp:forward>
</body>
</html>