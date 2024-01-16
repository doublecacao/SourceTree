<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
    <s:authentication property="principal.mvo.authList" var="auths"/>
<jsp:include page="../layout/header.jsp"/>
<div class="container-md">
    <h2>회원정보 수정</h2>
    
<form action="/member/modify" method="post">
<!-- name이 중요하다 -->
<%--     <s:authentication property="principal.mvo.email" var="authEmail"/>
    <s:authentication property="principal.mvo.nickName" var="authNick"/> --%>
	<div class="mb-3">
	  <label for="email" class="form-label">이메일</label>
	  <input type="email" name="email" class="form-control" id="email" value="${mvo.email }" readonly="readonly">
	</div>
	<div class="mb-3">
	  <label for="pwd" class="form-label">비밀번호</label>
	  <input type="password" name="pwd" class="form-control" id="pwd" placeholder="PassWord...">
	</div>
	<div class="mb-3">
	  <label for="nickName" class="form-label">닉네임</label>
	  <input type="text" name="nickName" class="form-control" id="nickName" value="${mvo.nickName }">
	</div>
	
	<!-- 해당 멤버의 권한을 출력 -->
	<h3>권한 수정</h3>
	<c:forEach items="${mvoAuth }" var="mvoAuth">
		<hr>
		<h5><a href="/member/auth">${mvoAuth.auth }</a></h5>
	</c:forEach>
    
    <button type="submit" class="btn btn-primary">수정</button>
    <a href="/member/delete?email=${mvo.email }"><button type="button" class="btn btn-danger">탈퇴</button></a>
</form>
</div>
<jsp:include page="../layout/footer.jsp"/>