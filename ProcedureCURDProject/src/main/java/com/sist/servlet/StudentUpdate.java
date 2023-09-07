package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.StudentDAO;
import com.sist.dao.StudentVO;


@WebServlet("/StudentUpdate")
public class StudentUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/html;charset=UTF-8");
		
		String hakbun=request.getParameter("hakbun");
		StudentDAO dao=StudentDAO.newInstance();
		StudentVO vo=dao.studentDetail(Integer.parseInt(hakbun));

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
		out.println("<h1>학생 수정</h1>");
		out.println("<div class=row>");
		out.println("<form method=post action=StudentUpdate>");
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
		out.println("<input type=hidden name=hakbun value="+vo.getHakbun()+">");
		// hidden= 출력없이 데이터만 전송할 목정 > 번호, 페이지 => 사용자 중심
		out.println("<input type=text name=name size=20 class=input-sm value="+vo.getName()+">");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=35%>국어</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=kor size=20 class=input-sm max=100 min=0 step=5 value="+vo.getKor()+">");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=35%>영어</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=eng size=20 class=input-sm max=100 min=0 step=5 value="+vo.getEng()+">");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=35%>수학</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=math size=20 class=input-sm max=100 min=0 step=5 value="+vo.getMath()+">");
		out.println("</td>");
		out.println("</tr>");

		
		out.println("<tr>");
		out.println("<td colspan=2 class=text-center>");
		out.println("<input type=submit value=수정 class=\"btn btn-sm btn-warning\">");
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
			String name,kor,eng,math,hakbun;
			name=request.getParameter("name");
			kor=request.getParameter("kor");
			eng=request.getParameter("eng");
			math=request.getParameter("math");
			hakbun=request.getParameter("hakbun");
			
			StudentVO vo=new StudentVO();
			vo.setName(name);
			vo.setKor(Integer.parseInt(kor));
			vo.setEng(Integer.parseInt(eng));
			vo.setMath(Integer.parseInt(math));
			vo.setHakbun(Integer.parseInt(hakbun));
			
			StudentDAO dao=StudentDAO.newInstance();
			dao.studentUpdate(vo);
			
			response.sendRedirect("StudentList");
			
	}

}
