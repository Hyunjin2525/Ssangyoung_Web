<%--
	import => 라이브러리
	import = "java.util.*,java.io.*";
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.*" buffer="16kb" %>
<%@ page import="com.sist.*" %>
<%
	Date date=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	String today=sdf.format(date);
	MainClass m=new MainClass();
	String ms=m.display();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	오늘 날짜는 <%=today %>
	<h1><%=ms %></h1>
	<h1><%= out.getBufferSize() %></h1>
	<h1><%= out.getRemaining() %></h1>
	<h1><%= out.getBufferSize()-out.getRemaining()%></h1>
</body>
</html>