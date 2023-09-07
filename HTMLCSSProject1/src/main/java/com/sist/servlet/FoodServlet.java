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
 * Servlet implementation class FoodServlet
 */
@WebServlet("/FoodServlet")
public class FoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		FoodDAO dao=FoodDAO.newInstance();
		List<FoodVO> list=dao.foodAllData();
		PrintWriter out=response.getWriter();
		// => 브라우저에 출력하는 내용물 => HTML
		out.println("<html>");
		out.println("<head>");
		
		out.println("<script src=\"http://code.jquery.com/jquery.js\"></script>");
		out.println("<script>");
		out.println("$(function(){");
		out.println("$('#keyword').keyup(function(){");
		out.println("let k=$(this).val();");
		out.println("console.log(k)");
		out.println("$('#user-table > tbody > tr').hide()");
		out.println("let temp=$('#user-table > tbody > tr > td:nth-child(5n+2):contains(\"'+k+'\")');");
		out.println("$(temp).parent().show();");
		out.println("})");
		out.println("})");
		out.println("</script>");
		
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>맛집 목록</h1>");
		
		out.println("<table border=1 bordercolor=black width=1024>");
		out.println("<tr>");
		out.println("<td>");
		// input[type="text"] = 속성선택자
		out.println("<input type=text size=30 id=keyword>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<table border=1 bordercolor=black width=1024 id=user-table>");
		
		out.println("<thead>"); //한번만 실행
		out.println("<tr>");
		out.println("<th></th>");
		out.println("<th>맛집명</th>");
		out.println("<th>주소</th>");
		out.println("<th>전화</th>");
		out.println("<th>음식종류</th>");
		out.println("</tr>");
		out.println("</thead>");
		out.println("<tbody>"); //Vue,React (view) => table출력이 안됨
		for(FoodVO vo:list)
		{
			out.println("<tr>");
			out.println("<td><img src="+vo.getPoster()+"width=3 height=30></td>");
			out.println("<td>"+vo.getName()+"</td>");
			out.println("<td>"+vo.getAddress()+"</td>");
			out.println("<td>"+vo.getTel()+"</td>");
			out.println("<td>"+vo.getType()+"</td>");
			out.println("</tr>");
		}
		out.println("</tbody>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
		
		
		
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
