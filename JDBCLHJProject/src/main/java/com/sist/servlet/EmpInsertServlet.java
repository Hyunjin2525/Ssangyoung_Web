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
 * Servlet implementation class EmpInsertServlet
 */
@WebServlet("/EmpInsertServlet")
public class EmpInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//입력 폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		EmpDAO dao=new EmpDAO();
		List<Integer> mlist=dao.empGetMgrData();
		List<Integer> dlist=dao.empGetDeptnoData();
		List<Integer> slist=dao.empGetSalData();
		List<String> jlist=dao.empGetJobData();
		
		
		 out.write("<!DOCTYPE html>");
	      out.write("<html>");
	      out.write("<head>");
	      out.write("<meta charset=\"UTF-8\">");
	      out.write("<title>Insert title here</title>");
	      out.write("   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
	      out.write("  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
	      out.write("<style>");
	      out.write(".container{margin-top:50px}");
	      out.write(".row{margin:0px auto;width:600px}");
	      out.write("h1{text-align:center}");
	      out.write("</style>");
	      out.write("</head>");
	      
	      out.write("<body>");

	      out.write("<div class=container>");
	      out.write("<h1>사원등록</h1>");
	      out.write("<div class=row>");
	      out.write("<form method=post action=EmpInsertServlet>"); //EmpInsertServlet가 가지고 있는 포스털르 호출
	      out.write("<table class=table>");
	      
	      out.write("<tr>");
	      out.write("<th width=20% class=\"text-right success\">이름</th>");
	      out.write("<td width=80%><input type=text name=ename class=input-sm size=15 required></td>");
	      out.write("</tr>");
	      out.write("<tr>");
	      out.write("<th width=20% class=\"text-right success\">직위</th>");
	      out.write("<td width=80%>");
	      out.write("<select name=job class=input-sm>");
	      for(String j:jlist)
	      {
	    	  out.write("<option>"+j+"</option>");
	      }
	      out.write("</select>");
	      out.write("</td>");
	      out.write("</tr>");
	      out.write("<tr>");
	      out.write("<th width=20% class=\"text-right success\">사수번호</th>");
	      out.write("<td width=80%>");
	      out.write("<select name=mgr class=input-sm>");
	      for(int m:mlist)
	      {
	    	  if(m==0) continue;
	    	  out.write("<option>"+m+"</option>");
	      }
	      out.write("</select>");
	      out.write("</td>");
	      out.write("</tr>");
	      out.write("<tr>");
	      out.write("<th width=20% class=\"text-right success\">급여</th>");
	      out.write("<td width=80%>");
	      out.write("<select name=sal class=input-sm>");
	      for(int s:slist)
	      {
	    	  out.write("<option>"+s+"</option>");
	      }
	      out.write("</select>");
	      out.write("</td>");
	      out.write("</tr>");
	      out.write("<tr>");
	      out.write("<th width=20% class=\"text-right success\">성과급</th>");
	      out.write("<td width=80%><input type=number name=comm class=input-sm size=15 max=500 min=100 step=50 required></td>");
	      out.write("</tr>");
	      out.write("<tr>");
	      out.write("<th width=20% class=\"text-right success\">부서번호</th>");
	      out.write("<td width=80%>");
	      out.write("<select name=deptno class=input-sm>");
	      for(int d:dlist)
	      {
	    	  out.write("<option>"+d+"</option>");
	      }
	      out.write("</select>");
	      out.write("</td>");
	      out.write("</tr>");
	      
	      out.write("<tr>");
	      out.write("<td colspan=2 class=text-center>");
	      out.write("<button class=\"btn btn-sm btn-primary\"> 등록</button>&nbsp;");
	      out.write("<input type=button value=취소 class=\"btn btn-sm btn-primary\" onclick=\"javascript:history.back()\" >");
	      
	      out.write("</td>");
	      out.write("</tr>");

	      
	      out.write("</table>");
	      out.write("</form>");
	      out.write("</div>");
	      out.write("</div>");
	      out.write("</body>");
	      out.write("</html>");
		
	}

	// 입력된 값 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.한글변환 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		request.setCharacterEncoding("UTF-8");
		String ename=request.getParameter("ename");
		String job=request.getParameter("job");
		String mgr=request.getParameter("mgr");
		String sal=request.getParameter("sal");
		String comm=request.getParameter("comm");
		String deptno=request.getParameter("deptno");
		
		EmpVO vo=new EmpVO();
		vo.setEname(ename);
		vo.setJob(job);
		vo.setMgr(Integer.parseInt(mgr));
		vo.setSal(Integer.parseInt(sal));
		vo.setComm(Integer.parseInt(comm));
		vo.setDeptno(Integer.parseInt(deptno));
		
		EmpDAO dao=new EmpDAO();
		dao.empInsert(vo);
		
	

		response.sendRedirect("MainServlet");
		
		
	}

}
