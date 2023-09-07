<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,java.util.*"%>
<%

	FoodDAO dao=new FoodDAO();

	List<CategoryVO> list=dao.foodCategoryData();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
	margin-top: 50px;
}
.row{
	margin: 0px auto;
	width:100%;
}
</style>
</head>
<body>
	<div class="container">
	<h1>믿고 보는 맛집 리스트</h1>
	<hr>
		<div class="row">
			<%
				for(int i=0;i<12;i++)
				{
					CategoryVO vo=list.get(i);
			%>
				 <div class="col-md-3">
   					 <div class="thumbnail">
      					<a href="#">
       						<img src="<%= vo.getPoster() %>" title="<%= vo.getSubject() %>" style="width:100%">
       						<div class="caption">
          						<p><%=vo.getTitle() %></p>
        					</div>
    					</a>
    			</div>
    			</div>
			<% 
			
				}
			%>
		</div>
		<h1>지역별 인기 맛집 리스트</h1>
	<hr>
		<div class="row">
			<%
				for(int i=12;i<18;i++)
				{
					CategoryVO vo=list.get(i);
			%>
				 <div class="col-md-4">
   					<div class="thumbnail">
      					<a href="#">
       						<img src="<%= vo.getPoster() %>" title="<%= vo.getSubject() %>" style="width:100%">
       						<div class="caption">
          						<p><%=vo.getTitle() %></p>
        					</div>
    					</a>
    			</div>
    			</div>
			<% 
			
				}
			%>
		</div>
		<h1>메뉴별 인기 맛집 리스트</h1>
	<hr>
		<div class="row">
			<%
				for(int i=18;i<30;i++)
				{
					CategoryVO vo=list.get(i);
			%>
				 <div class="col-md-3">
   					<div class="thumbnail">
      					<a href="#">
       						<img src="<%= vo.getPoster() %>" title="<%= vo.getSubject() %>" style="width:100%">
       						<div class="caption">
          						<p><%=vo.getTitle() %></p>
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