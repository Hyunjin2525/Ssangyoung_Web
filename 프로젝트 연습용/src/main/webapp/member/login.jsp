<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<title>Clean Blog - Start Bootstrap Theme</title>
	<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
	<!-- Font Awesome icons (free version)-->
	<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
	<!-- Google fonts-->
	<link href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800" rel="stylesheet" type="text/css" />
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400&display=swap" rel="stylesheet"> <!-- 한글 폰트 -->
	<!-- Core theme CSS (includes Bootstrap)-->
	<link href="../main/css/styles.css" rel="stylesheet" />
<style type="text/css">
	#top {
		height : 80px;
	}
	
	#wrap {
		background-color: red;
		height: 92vh;
	}
	
	#blank {
		height : 140px;
	}
	
	#login_form {
		font-family: 'Noto Sans KR', sans-serif;
		width : 800px;
		background-color: white;
		padding : 100px;
		border-radius: 50px;
	}
	label {
		font-size : 17px;
	}
	
	#join_text {
		font-size : 14px;
	}
	
	#join_text > a:hover {
		text-decoration: underline;
	}

</style>
</head>
<body>

<div id=top>
	<nav class="navbar navbar-expand-lg navbar-light" id="mainNav">
		<div class="container px-4 px-lg-5">
		<a class="navbar-brand" href="../main/index.html">football-play</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
		Menu
		<i class="fas fa-bars"></i>
		</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ms-auto py-4 py-lg-0">
					<li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href="../main/index.html">구장예약</a></li>
					<li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href="about.html">게시판</a></li>
					<li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href="post.html">마이페이지</a></li>
					<li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href="contact.html">쇼핑몰</a></li>
					<li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href="../member/login.jsp">로그인</a></li>
				</ul>
			</div>
		</div>
	</nav>
</div>

<!-- 로그인 폼 -->
<div id="wrap" style="background-image: url(../main/assets/img/login-bg.jpg); background-size: cover;">
	<div id=blank></div>
	
	<div class="container" id="login_form">
		<div class="row text-center">
			<span class="text-dark h4" style="font-size: 30px">LOGIN</span>
		</div>
		
		<form method=post>
			<div class="form-group">
				<label class="form-label mt-4">아이디</label>
				<input type=text class="form-control" name=id>
			</div>
			<div class="form-group">
				<label class="form-label mt-4">비밀번호</label>
				<input type=password class="form-control" name=pwd>
			</div>
			<div class="d-grid gap-2 pt-5">
				<button class="btn btn-danger btn-lg">로그인</button>
			</div>
		</form>
		
		<div class="form-group text-center">
			<label class="form label mt-4" id="join_text">아직 회원이 아니라면? <a href="../member/join.jsp" style="color: #dc3545;"><b>회원가입</b></a></label>
		</div>
		
	</div>
</div>
</body>
</html>