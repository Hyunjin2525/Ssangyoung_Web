<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
   <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="MainServlet">WebSiteName</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="MainServlet">Home</a></li>
      <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">서울 맛집<span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="MainServlet">맛집 목록</a></li>
          <li><a href="MainServlet?mode=4">맛집 검색</a></li>
        </ul>
      </li>
       <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">서울 여행<span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">명소</a></li>
          <li><a href="#">자연&관광</a></li>
          <li><a href="#">쇼핑</a></li>
        </ul>
      </li>

    </ul>
  </div>
</nav>
  
<div class="container">
  <h3>Navbar With Dropdown</h3>
  <p>This example adds a dropdown menu for the "Page 1" button in the navigation bar.</p>
</div>

</body>
</html>