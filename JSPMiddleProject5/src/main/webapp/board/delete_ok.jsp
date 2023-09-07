<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<jsp:useBean id="dao" class="com.sist.dao.ReplyBoardDAO"></jsp:useBean>

<%
	String no=request.getParameter("no");
	String pwd=request.getParameter("pwd");
	boolean bcheck=dao.boardDelete(Integer.parseInt(no), pwd);
	request.setAttribute("bcheck", bcheck);
%>
<c:if test="${bcheck==true }">
	<c:redirect url="list.jsp"></c:redirect>
</c:if>
<c:if test="${bcheck==false }">
	<script>
		alert("비밀번호가 틀립니다")
		history.back()
	</script>
</c:if>