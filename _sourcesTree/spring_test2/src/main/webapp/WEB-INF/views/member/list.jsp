<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<jsp:include page="../layout/header.jsp"/>
    
<!-- name이 중요하다 -->
	<h3>회원 목록</h3>
	<table class="table">
  <thead>
    <tr>
      <th scope="col">이메일</th>
      <th scope="col">닉네임</th>
      <th scope="col">가입일자</th>
      <th scope="col">최종 접속일</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${mvoList }" var="mvoList">
  	<tr>
  		<th scope="row"><a href="/member/modify?email=${mvoList.email}">${mvoList.email}</a></th>
  		<td>${mvoList.nickName}</td>
  		<td>${mvoList.regDate}</td>
  		<td>${mvoList.lastLogin}</td>
  	</tr>
  </c:forEach>
  </tbody>
</table>


<jsp:include page="../layout/footer.jsp"/>