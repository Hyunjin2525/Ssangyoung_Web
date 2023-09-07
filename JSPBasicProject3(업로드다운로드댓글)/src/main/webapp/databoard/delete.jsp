<%@page import="com.sist.vo.DataBoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,java.io.*"%>
    
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"></jsp:useBean>
<%
	String no=request.getParameter("no");
	String pwd=request.getParameter("pwd");
	
	DataBoardVO vo=dao.databoardFileInfo(Integer.parseInt(no));
	//dao연결
	boolean bCheck=dao.databoardDelete(Integer.parseInt(no), pwd);
	
	if(bCheck==true)
	{
		if(vo.getFileSize()!=0) //파일이 존재 하면
		{
			File file=new File("c:\\download\\"+vo.getFileName());
			file.delete();
			
		}
		response.sendRedirect("list.jsp");
	}
	else
	{
%>
	<script text="text/javascript">
		alert("비밀번호가 틀립니다!!")
		history.back();
	</script>
<%
		
	}
%>