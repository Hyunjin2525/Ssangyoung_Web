package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
/**
 * Servlet implementation class EmpListServlet
 */
@WebServlet("/EmpListServlet")
public class EmpListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		EmpDAO dao=new EmpDAO();
		List<EmpVO> list=dao.empListData();
			  out.write("<!DOCTYPE html>");
		      out.write("<html>");
		      out.write("<head>");
		      out.write("<meta charset=\"UTF-8\">");
		      out.write("<title>Insert title here</title>");
		      out.write("   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		      out.write("  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		      out.write("<style>");
		      out.write(".container{margin-top:50px}");
		      out.write(".row{margin:0px auto;width:800px}");
		      out.write("h1{text-align:center}");
		      out.write("</style>");
		      out.write("</head>");
		      
		      out.write("<body>");

		      out.write("<div class=container>");
		      out.write("<h1>사원 목록</h1>");
		      out.write("<div class=row>");
		      out.write("<table class=\"table table-striped\">");
		      out.write("<tr class=success>");
		      out.write("<th class=text-center>사번</th>");
		      out.write("<th class=text-center>이름</th>");
		      out.write("<th class=text-center>직위</th>");
		      out.write("<th class=text-center>입사일</th>");
		      out.write("<th class=text-center>급여</th>");
		      out.write("<th class=text-center>성과급</th>");
		      out.write("<th class=text-center>부서명</th>");
		      out.write("<th class=text-center>근무지</th>");
		      out.write("<th class=text-center>호봉</th>");
		      out.write("</tr>");
		      for(EmpVO vo:list)
		      {
		          out.write("<tr>");
			      out.write("<td class=text-center>"+vo.getEmpno()+"</td>");
			      out.write("<td class=text-center><a href=\"MainServlet?mode=2&empno="+vo.getEmpno()+"\">"+vo.getEname()+"</a></td>");
			      out.write("<td class=text-center>"+vo.getJob()+"</td>");
			      out.write("<td class=text-center>"+vo.getDbday()+"</td>");
			      out.write("<td class=text-center>"+vo.getDbsal()+"</td>");
			      out.write("<td class=text-center>"+vo.getComm()+"</td>");
			      out.write("<td class=text-center>"+vo.getDvo().getDname()+"</td>");
			      out.write("<td class=text-center>"+vo.getDvo().getLoc()+"</td>");
			      out.write("<td class=text-center>"+vo.getSco().getGrade()+"</td>");
			      out.write("</tr>");
		      }
		      out.write("</table>");
		      
		      out.write("</body>");
		      
		      out.write("</html>");
	}

}
