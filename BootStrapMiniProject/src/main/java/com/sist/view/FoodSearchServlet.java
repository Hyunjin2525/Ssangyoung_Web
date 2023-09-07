package com.sist.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import com.sist.dao.*;
@WebServlet("/FoodSearchServlet")
public class FoodSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
/*
 *  @GetMapping => GET
 *  @PostMapping => POST
 *  @RequestMapping => GET/POST
 *  ------------------------------ 400
 */

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// 사용자 요청값을 받는다
		String addr=request.getParameter("addr");
		if(addr==null)
		{
			addr="마포";
		}
		
		String strPage=request.getParameter("page");
		if(strPage==null)
		{
			strPage="1";
		}
		
		int curpage=Integer.parseInt(strPage);
		
		FoodDAO dao=FoodDAO.newInstance();
		List<FoodVO> list=dao.foodFindData(addr, curpage);
		int totalpage=(int)(Math.ceil(dao.foodRowCount(addr)/12.0));
		int count=dao.foodRowCount(addr);
		final int BLOCK=5;
		//curpage=1 => startpage=1
		//curpage=2==>
		int startrPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		//<[1][2][3][4][5]
		//curpage => 1~5 => startpage=1, endpage5
		//<[6][7][8][9]10]
		//화면
		/*
		 * <ul class="pagination">
  <li><a href="#">1</a></li>
  <li><a href="#">2</a></li>
  <li><a href="#">3</a></li>
  <li><a href="#">4</a></li>
  <li><a href="#">5</a></li>
</ul>
		 * 
		 */
		if(endpage>totalpage)
			endpage=totalpage;// 맨 마지막 페이지는 totalpage만큼 출력
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}");
		out.println("row{");
		out.println("margin:0px auto;");
		out.println("width:900px}</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<div class=table>");
		out.println("<tr>");
		
		out.println("<td>");
		
		out.println("<form method=post action=MainServlet>");
		out.println("<input type=hidden name=mode value=4>");
		out.println("<input type=text name=addr size=25 class=input-sm>");
		out.println("<input type=submit value=검색 class=\"btn btn-sm btn-danger\">");
		out.println("</form>");
		
		out.println("</td>");
		
		out.println("<tr/>");
		out.println("</table>");
		out.println("<div style=\"height:30px\"></div>");
		for(FoodVO vo:list)
		{
			out.println(" <div class=\"col-md-3\">");// 한줄에 4개 출력 (12가 되면 밑으로 내려간다)
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=\"#\">");
			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:100%\">");
			out.println("<div class=\"caption\">");
			out.println("<p style=\"font-size:15px\">"+vo.getName()+"</p>");
			out.println("</div>");
			out.println(" </a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>");//row
		out.println("<div style=\"height:30px\"></div>");
		out.println("<div class=row>");
		out.println("<div class=text-center>");
		
		out.println("<ul class=pagination>");
		out.println("<li><a href=#>&lt;</a></li>");
		for(int i=startrPage;i<=endpage;i++)
		{
			out.println("<li "+(curpage==i?"class=active":"")+"><a href=MainServlet?addr="+addr+"&mode=4&page="+i+">"+i+"</a></li>");
		}
		out.println("<li><a href=#>&gt;</a></li>");
		out.println("</ul>");
		
		out.println("</div>");
		out.println("</div>");//container
		out.println("</body>");
		out.println("</html>");
		
	}

}
