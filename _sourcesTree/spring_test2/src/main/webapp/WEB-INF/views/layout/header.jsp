<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary shadow p-3 mb-5 bg-body-tertiary rounded">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Spring</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/">Home</a>
        </li>
        <!-- 현재 인증한 사용자의 정보를 가져와서 해당 권한이 있는지 확인 -->
        <!-- 현재 사용자의 정보는 principal -->
		<!-- anyMatch() : stream 매칭 메서드(최소한 1개의 요소가 주어진 조건에 맞는지 확인) -->
        <s:authorize access="isAuthenticated()">
        <s:authentication property="principal.mvo.email" var="authEmail"/>
        <s:authentication property="principal.mvo.nickName" var="authNick"/>
        <s:authentication property="principal.mvo.authList" var="auths"/>
        
		<c:choose>
			<c:when test="${auths.stream().anyMatch(AuthVO -> AuthVO.auth.equals('ROLE_ADMIN')).get() }">
		        <li class="nav-item">
		          <a class="nav-link" href="/member/list">회원리스트</a>
		        </li>
			</c:when>
		</c:choose>        
        
        <li class="nav-item">
          <a class="nav-link" href="/board/register">게시판 글쓰기</a>	<!-- 여기서의 경로는 컨트롤러로 간다(컨트롤러에서 웹페이지로 경로를 지정해주는 것) -->
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/board/list">게시판 보기</a>
        </li>
        
        <form action="/member/logout" method="post" id="logoutForm">
        	<input type="hidden" name="email" value="authEmail">
        </form>
        <li class="nav-item" style="cursor: pointer;">
          <a class="nav-link" id="logoutLink">로그아웃</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/member/modify?email=${authEmail }">${authNick } (${authEmail })님 어서오세요</a>
        </li>
        </s:authorize>
        <s:authorize access="isAnonymous()">
	        <li class="nav-item">
	          <a class="nav-link" href="/member/register">회원가입</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="/member/login">로그인</a>
	        </li>
        </s:authorize>
      </ul>
    </div>
  </div> 
</nav>
<script type="text/javascript" >
document.getElementById('logoutLink').addEventListener('click', (e)=>{
    e.preventDefault();
    document.getElementById('logoutForm').submit(); //form태그 전송
})
</script>
