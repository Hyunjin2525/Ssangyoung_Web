<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	session.invalidate(); //전체 해제  
	response.sendRedirect("goods.jsp");
%>