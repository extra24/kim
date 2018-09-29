<%@page import="jdbc.ConnectionProvider"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>dbTest.jsp</title>
</head>
<body>
디비확인
<%
	try(Connection conn = ConnectionProvider.getConnection()){
		out.println("커넥션 성공");
	}catch(SQLException e){
		out.println("커넥션 실패");
	}
%>
</body>
</html>