<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 	//쿠키를 생성해서 전송
 	String fno=request.getParameter("fno");
 	//1. 쿠키 생성
 	Cookie cookie=new Cookie("food_"+fno,fno); //중복된 값은 저장 하지 않기 위해 fno를 붙인다
 	//2. 저장 기간 설정
 	cookie.setMaxAge(60*60*24); //하루 저장
 	//3. 경로 지정
 	cookie.setPath("/");
 	//4.클라이언트 브라으저로 전송
 	response.addCookie(cookie);
 	//전송 끝나면 => detail로 이동
 	response.sendRedirect("detail.jsp?fno="+fno);
	
 %>
