<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="dao" class="com.sist.dao.ReplyBoardDAO"/>
<%
	//사용자가 보낸 데이터 읽기
	request.setCharacterEncoding("UTF-8");
	
%>
<%-- 사용자가 보내준 데이터를 vo객체에 첨부 --%>
<jsp:useBean id="vo" class="com.sist.dao.ReplyBoardVO">
	<jsp:setProperty name="vo" property="*"/>
</jsp:useBean>
<%
	
	request.setAttribute("vo", vo);
	//dao.boardInsert(vo);
	boolean bcheck=dao.boardUpdate(vo);
	request.setAttribute("bcheck", bcheck);
%>
<<c:choose>
	<c:when test="${bcheck==true }">
		<c:redirect url="detail.jsp?no=${vo.no }"></c:redirect>
	</c:when>
	<c:otherwise>
		<script>
			alert("비밀번호가 틀립니다")
			history.back()
		</script>
	</c:otherwise>
</c:choose> 

 <c:redirect url="detail.jsp?no=${ vo.no}"/>