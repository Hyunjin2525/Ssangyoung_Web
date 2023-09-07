package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.filters.SetCharacterEncodingFilter;

import java.util.*;
import com.sist.dao.*;
/**
 * Servlet implementation class StudentInsert
 */
@WebServlet("/StudentInsert")
public class StudentInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}");
		out.println(".row{margin:0px auto;width:350px;}");
		out.println("h1{text-align:center}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h1>학생 등록</h1>");
		out.println("<div class=row>");
		out.println("<form method=post action=StudentInsert>");
		//post = > dopost
		// 나머지는 doGet
		/*
		 *  <form method=get> doGet()
		 *  <form method=aaa> doGet()
		 *  <form> doGet()
		 */
		
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td width=35%>이름</td>");
		out.println("<td width=65%>");
		out.println("<input type=text name=name size=20 class=input-sm>");
		out.println("</td>");
		out.println("</tr>");
		
		
		out.println("<tr>");
		out.println("<td width=35%>학번</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=hakbun size=20 class=input-sm max=100 min=0 step=5 value=50>");
		out.println("</td>");
		out.println("</tr>");
	
		
		
		out.println("<tr>");
		out.println("<td width=35%>국어</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=kor size=20 class=input-sm max=100 min=0 step=5 value=50>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=35%>영어</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=eng size=20 class=input-sm max=100 min=0 step=5 value=50>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=35%>수학</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=math size=20 class=input-sm max=100 min=0 step=5 value=50>");
		out.println("</td>");
		out.println("</tr>");

		
		out.println("<tr>");
		out.println("<td colspan=2 class=text-center>");
		out.println("<input type=submit value=등록 class=\"btn btn-sm btn-warning\">");
		out.println("<input type=button value=취소 class=\"btn btn-sm btn-warning\" onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</form>"); //호출 => submit버튼 클릭시
		out.println("</div>");//row
		out.println("</div>");//container
		out.println("</body>");
		out.println("</html>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//servlet에서만 적용되는 것이 아니라 jsp/spring포함
										//------- servlet으로 만들어져 있다		
			String name,kor,eng,math;
			name=request.getParameter("name");
			kor=request.getParameter("kor");
			eng=request.getParameter("eng");
			math=request.getParameter("math");
			
		StudentVO vo=new StudentVO();
		vo.setName(name);
		vo.setKor(Integer.parseInt(kor));
		vo.setEng(Integer.parseInt(eng));
		vo.setMath(Integer.parseInt(math));
		
		StudentDAO dao=StudentDAO.newInstance();
		dao.studentInsert(vo);
		
		response.sendRedirect("StudentList");
	}

}
