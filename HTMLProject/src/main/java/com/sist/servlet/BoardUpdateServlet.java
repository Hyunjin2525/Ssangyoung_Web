package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVo;


@WebServlet("/BoardUpdateServlet")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//전송 방식
			response.setContentType("text/html;charset=UTF-8");
			
			//클라이언트가 보낸 값을 받는다
			// => BoardDetailServlet?no=
			String no=request.getParameter("no");
			/*
			 * ?no=1&page=10&name=mmm
			 * &로 구분
			 * ajax => {"no":1}
			 * react/vue=>params:{"no":1"}
			 */
			//오라클에서 값을 받아옴
			BoardDAO dao = BoardDAO.newInstance();
			BoardVo vo = dao.boardDetailData2(Integer.parseInt(no));
			
			//브라우저가 읽을 수 있게 출력
			PrintWriter out = response.getWriter();
			
			out.println("<html>");
			out.println("<head>");
			out.println("<link rel=stylesheet href=html/table.css>");
			out.println("</head>");
			out.println("<body>");
			out.println("<center>");
			out.println("<h1>내용보기</h1>");
			out.println("<table width=700 class=table_content");
			out.println("<tr>");
			out.println("<th width=20%>번호</th>");
			out.println("<td width=30% align=center>"+vo.getNo()+"</td>");
			out.println("<th width=20%>작성일</th>");
			out.println("<td width=30% align=center>"+vo.getDbday()+"</td>");
			out.println("</tr>");

			out.println("<tr>");
			out.println("<th width=20%>이름</th>");
			out.println("<td width=30% align=center>"+vo.getName()+"</td>");
			out.println("<th width=20%>조회수</th>");
			out.println("<td width=30% align=center>"+vo.getHit()+"</td>");
			out.println("</tr>");

			out.println("<tr>");
			out.println("<th width=20%>제목</th>");
			out.println("<td colspan=3><input type=text name=subject size=50 required></td>"); //value
			out.println("</tr>");

			out.println("<tr>");
			out.println("<th width=25%>내용</th>");
			out.println("<td width=85%><textarea rows=10 cols=50 name=content required></textarea></td>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<th width=25%>비밀번호</th>");
			out.println("<td width=85%><input type=password name=pwd size=10 required></td>");
			out.println("</tr>");

			out.println("<tr>");
			out.println("<td colspan=4 align=right>");
			out.println("<a href=BoardUpdateServlet?no="+vo.getNo()+">수정</a>&nbsp;");
			out.println("<a href=BoardListServlet>목록</a>");
			out.println("</td>");
			out.println("</tr>");
			
	

			out.println("</table>");
			out.println("</center>");
			out.println("</body>");
			out.println("</html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
