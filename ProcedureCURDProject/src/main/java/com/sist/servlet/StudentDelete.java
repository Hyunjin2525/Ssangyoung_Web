package com.sist.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;
/**
 * Servlet implementation class StudentDelete
 */
@WebServlet("/StudentDelete")
public class StudentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hakbun=request.getParameter("hakbun");
		
		StudentDAO dao=StudentDAO.newInstance();
		dao.studentDelete(Integer.parseInt(hakbun));
		
		// 목록으로 이동 
		response.sendRedirect("StudentList");
	}

}
