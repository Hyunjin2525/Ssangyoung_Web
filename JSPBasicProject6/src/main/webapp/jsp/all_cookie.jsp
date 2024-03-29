<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,java.util.*"%>
    <%
    	Cookie[] cookies=request.getCookies();
    	FoodDAO dao=FoodDAO.newInstance();
    	List<FoodBean> clist=new ArrayList<FoodBean>();
    	if(cookies!=null)
    	{
    		for(int i=0;i<cookies.length;i++)
    		{
    			if(cookies[i].getName().startsWith("food_"))
    			{
    				String fno=cookies[i].getValue();
    				FoodBean vo=dao.foodDetailData(Integer.parseInt(fno));
    				clist.add(vo);
    			}
    		}
    	}
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.cotainer{
	margin-top: 50px;
}
.row{
	margin: 0px auto;
	width: 960px;
}
</style> 
</head>
<body>
	<div class="container">
		<h1 class="text-center">쿠키목록</h1>
		<div class="row">
		<%-- 맛집 목록 --%>
		<%
			for(FoodBean vo:clist)
			{
		%>
				<div class="col-md-3">
    				<div class="thumbnail">
    						<a href="detail_before.jsp?fno=<%=vo.getFno()%>">
        					<img src="<%=vo.getPoster() %>" alt="Lights" style="width:100%">
        					<div class="caption">
          						<p><%=vo.getName() %></p>
        					</div>
      						</a>
    				</div>
  				</div>
		<% 
				
			}
		%>
		</div>
		</div>
</body>
</html>