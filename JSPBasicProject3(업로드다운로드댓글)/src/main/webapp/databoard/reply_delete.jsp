<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"></jsp:useBean>
<%
	String no=request.getParameter("no");
	String bno=request.getParameter("bno"); // 삭제 후 이동할 때 필요하기 때문에 받는다
	
	dao.replyDelete(Integer.parseInt(no));
	
	response.sendRedirect("detail.jsp?no="+bno); // detail에서 no를 받는다
%>