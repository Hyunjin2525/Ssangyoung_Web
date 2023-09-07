<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//response.sendRedirect("response_2.jsp");
	RequestDispatcher re=request.getRequestDispatcher("response_2.jsp");
	re.forward(request, response); 	//request를 초기화 시키지 않고 값을 보낼때 //붙여넣기 개념
	System.out.println(request);  
	

	
	
%>
