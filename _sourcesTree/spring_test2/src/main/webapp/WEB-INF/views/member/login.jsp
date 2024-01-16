<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<jsp:include page="../layout/header.jsp"/>

<html>

<div class="container-md">
	<h2>로그인</h2>
	<form action="/member/login" method="post">
		<c:if test="${not empty param.errorMsg }">
			<div class="mb-3 text-danger">
				<c:choose>
					<c:when test="${param.errorMsg eq 'Bad credentials' }">
						<c:set value="이메일 또는 비밀번호가 일치하지 않습니다." var="errorText"/>
					</c:when>
					<c:otherwise>
						<c:set value="관리자에게 문의해주세요." var="errorText"></c:set>
					</c:otherwise>
				</c:choose>
				${errorText }
			</div>
		</c:if>
		<div class="mb-3">
		  <label for="email" class="form-label">이메일</label>
		  <input type="text" name="email" class="form-control" id="email" placeholder="Email...">
		</div>
		<div class="mb-3">
		  <label for="pwd" class="form-label">비밀번호</label>
		  <input type="password" name="pwd" class="form-control" id="pwd" placeholder="PassWord...">
		</div>
	
		<button type="submit" class="btn btn-primary">로그인</button>
	</form>
</div>


</html>
<jsp:include page="../layout/footer.jsp"/>