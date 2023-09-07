package com.sist.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.*;

/**
 * Servlet implementation class FoodListServlet
 */
@WebServlet("/FoodListServlet")
public class FoodListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		String cno=request.getParameter("cno");
		
		FoodDAO dao=FoodDAO.newInstance();
		List<FoodVO> list=dao.food_category_data(Integer.parseInt(cno));
		CategoryVO cvo=dao.food_category_info(Integer.parseInt(cno));
	
		
		PrintWriter out=response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}");
		out.println(".row{");
		out.println("margin:0px auto;");
		out.println("width:800px}</style>");
		out.println("</head>");

		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<div class=jumbotron>");

		out.println("<center>");
		out.println("<h2>"+cvo.getTitle()+"</h2>");
		out.println("<h4>"+cvo.getSubject()+"</h4>");
		out.println("</center>");
		out.println("</div>");
		
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		for(FoodVO vo:list)
		{
			out.println("<table class=table>");
			out.println("<tr>");
			out.println("<td width=30% align=center rowspan=4>");
			out.println("<a href=FoodDetailBeforeServlet?mode=3&fno="+vo.getFno()+"><img src="+vo.getPoster()+" class=img-rounded style=\"width:240px;height:200px\">");
			out.println("</td>");
			out.println("<td width=70%><h3>");
			out.println("<a href=FoodDetailBeforeServlet?mode=3&fno="+vo.getFno()+">"+vo.getName()+"</a>&nbsp;<span style=\"color:orange\">"+vo.getScore()+"</span>");
			out.println("</h3></td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td width=70%>");
			out.println(vo.getAddress());
			out.println("</td>");
			out.println("</tr>");
			
			
			out.println("<tr>");
			out.println("<td width=70%>");
			out.println(vo.getPhone());
			out.println("</td>");
			out.println("</tr>");
			
			
			out.println("<tr>");
			out.println("<td width=70%>");
			out.println(vo.getType());
			out.println("</td>");
			out.println("</tr>");
			
		}
		
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		out.println("</div>");

		out.println("<div style=\"height:20px\"></div>");
		out.println("<div class=row>");
		out.println("<h3>최신 방문 맛집</h3>");
		out.println("<a href=\"FoodCookieDeleteServlet?mode=2&cno="+cno+"\" class=\"btn btn-xs btn-danger\">전체삭제</a>");
		out.println("<hr>");
		// Cookie처리
		// 1. Cookie읽기
		Cookie[] cookies=request.getCookies();
		List<FoodVO> clist=new ArrayList<FoodVO>();
		if(cookies!=null)
		{
			for(int i=cookies.length-1;i>=0;i--)//최신을 먼저 읽어온다
			{
				if(cookies[i].getName().startsWith("food_"))
				{
					//new Cookie("food_"+fno, fno);
					String fno=cookies[i].getValue();
					FoodVO vo=dao.foodDetailData(Integer.parseInt(fno));
					String temp=vo.getPoster();
					temp=temp.substring(0,temp.indexOf("^"));
					//테이블 컬럼에 저장시에는 한개의 값만 저장 => 관리가 쉽다
					// => 2정규화, 1정규화 => 중복을 제거, 3정규화 => 새로운 테이블
					vo.setPoster(temp);
					clist.add(vo);
				}
				//1.키(getName()),값(getValue())
				//request,response,session,cookie => Spring-Boot
			}
		}
		//데이터가 존재할 때
		int i=0;
		if(clist!=null && clist.size()>0)
		{
			for(FoodVO vo:clist) {
				
				if(i>7) break;
			out.println(" <div class=\"col-md-3\">");// 한줄에 4개 출력 (12가 되면 밑으로 내려간다)
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=\"MainServlet?mode=3&fno="+vo.getFno()+"\">");//쿠키 거치지 않고 바로 상세보기로 가게 끔 하기
			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:230px;height:200px\"></a>");
			out.println("<div class=\"caption\">");
			out.println("<p style=\"font-size:15px\">"+vo.getName()+"</p>");
			out.println("<p><a href=\"FoodCookieDelServlet?mode=2&cno="+vo.getCno()+"&fno="+vo.getFno()+"\" class=\"btn btn-xs btn-warning\">삭제</a> ");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			i++;
			}
		}
		out.println("</div>");
		out.println("</div>");
		
		out.println("</body>");
		out.println("</html>");
	}

}
