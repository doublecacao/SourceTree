<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<jsp:include page="../layout/header.jsp"/>
    <s:authentication property="principal.mvo.authList" var="auths"/>
<div class="container-md">
    <h2>회원정보 수정</h2>
    
<form action="/member/modify" method="post">
<!-- name이 중요하다 -->
<%--     <s:authentication property="principal.mvo.email" var="authEmail"/>
    <s:authentication property="principal.mvo.nickName" var="authNick"/> --%>
	<div class="col-12">
		<c:choose>
			<c:when test="${bdto.fvo.fileType eq 1 }">
				<div>
				<!-- /upload/save_dir/(여기까지 경로)uuid_file_name(파일명) -->
					<img alt="" src="/upload/${bdto.fvo.saveDir }/${bdto.fvo.uuid}_profile_${bdto.mvo.email}.png">
					<%-- <img alt="" class="img-fluid" src="/upload/${f:replace(bdto.flist.save_dir, '\\', '/') }/${bdto.flist.uuid}_th_${bdto.flist.file_name}"> --%>
				</div>
			</c:when>
			<c:otherwise>
				<div>
					<!-- 아이콘 같은 모양 하나 가져와서 넣기 -->
					<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-images" viewBox="0 0 16 16">
					  <path d="M4.502 9a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3z"/>
					  <path d="M14.002 13a2 2 0 0 1-2 2h-10a2 2 0 0 1-2-2V5A2 2 0 0 1 2 3a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v8a2 2 0 0 1-1.998 2zM14 2H4a1 1 0 0 0-1 1h9.002a2 2 0 0 1 2 2v7A1 1 0 0 0 15 11V3a1 1 0 0 0-1-1zM2.002 4a1 1 0 0 0-1 1v8l2.646-2.354a.5.5 0 0 1 .63-.062l2.66 1.773 3.71-3.71a.5.5 0 0 1 .577-.094l1.777 1.947V5a1 1 0 0 0-1-1h-10z"/>
					</svg>
				</div>
			</c:otherwise>
		</c:choose>
		</div>
	<div class="mb-3">
	  <label for="email" class="form-label">이메일</label>
	  <input type="email" name="email" class="form-control" id="email" value="${bdto.mvo.email }" readonly="readonly">
	</div>
	<div class="mb-3">
	  <label for="pwd" class="form-label">비밀번호</label>
	  <input type="password" name="pwd" class="form-control" id="pwd" placeholder="PassWord...">
	</div>
	<div class="mb-3">
	  <label for="nickName" class="form-label">닉네임</label>
	  <input type="text" name="nickName" class="form-control" id="nickName" value="${bdto.mvo.nickName }">
	</div>
	
	<!-- 해당 멤버의 권한을 출력 -->
	<h3>권한 목록</h3>
	<c:forEach items="${mvoAuth }" var="mvoAuth">
		<hr>
		<h5><a href="/member/auth">${mvoAuth.auth }</a></h5>
	</c:forEach>
    
    <button type="submit" class="btn btn-primary">수정</button>
    <a href="/member/delete?email=${bdto.mvo.email }"><button type="button" class="btn btn-danger">탈퇴</button></a>
</form>
</div>
<jsp:include page="../layout/footer.jsp"/>