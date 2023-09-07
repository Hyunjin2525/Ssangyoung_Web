<%@page import="com.sist.vo.DataBoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,java.io.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%
	//(post)
	//_ok.jsp : 기능처리 (member_ok, update_ok ....)
	// 데이터베이스 처리 => list.jsp
	//1.한글 처리
	request.setCharacterEncoding("UTF-8");
	//1-1.파일 업로드 클래스 생성
	String path="c:\\download";
	int size=1024*1024*100;
	String enctype="UTF-8";
	MultipartRequest mr=new MultipartRequest(request,path,size,enctype,new DefaultFileRenamePolicy());//new DefaultFileRenamePolicy() =중복 방지용(같은 파일 명이 있으면 숫자로 구분)
	//2.요청 데이터 받기
	String name=mr.getParameter("name");
	String subject=mr.getParameter("subject");
	String content=mr.getParameter("content");
	String pwd=mr.getParameter("pwd");
	//3.VO에 묶는다
	DataBoardVO vo=new DataBoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	//String filename=mr.getOriginalFileName("upload");//실제 사용자가 올린 파일명만 가져옴
	String filename=mr.getFilesystemName("upload"); //실제 변경된 파일명도 가져옴
	if(filename==null){// 업로드가 안된 상태
		vo.setFileName("");
		vo.setFileSize(0);
	}else{ //업로드가 된 상태
		File file=new File(path+"\\"+filename);
		vo.setFileName(filename);
		vo.setFileSize((int)file.length());
	}
	//4.DAO에 전송
	DataBoardDAO dao=DataBoardDAO.newInstance();
	dao.databoardInsert(vo);
	
	response.sendRedirect("list.jsp");
	//5.화면 이동 (list.jsp)
%>