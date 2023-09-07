<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	String fn=request.getParameter("fn");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<img src="../image/<%=fn %>" style="width:100%"> <%--파일이 밖에 있을 때는 ../ 붙인다 --%>
</body>
</html>