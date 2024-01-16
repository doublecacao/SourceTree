<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<jsp:include page="../layout/header.jsp"/>

<h2>게시글 목록</h2>
<br>

<table class="table">
  <thead>
    <tr>
      <th scope="col">번호</th>
      <th scope="col">제목</th>
      <th scope="col">작성자</th>
      <th scope="col">작성일자</th>
      <th scope="col">조회수</th>
      <th scope="col">댓글수</th>
      <th scope="col">파일수</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${list }" var="bvo">
  	<tr>
  	<!-- BoardVO에 있는 값 -->
  		<th scope="row">${bvo.bno }</th>
  		<td><a href="/board/detail?bno=${bvo.bno }">${bvo.title }</a></td>
  		<td>${bvo.writer }</td>
  		<td>${bvo.regDate }</td>
  		<td>${bvo.readCount }</td>
  		<td>${bvo.cmtQty }</td>
  		<td>${bvo.hasFile }</td>
  	</tr>
  </c:forEach>
  </tbody>
</table>
<!-- 페이징 라인 -->
<nav aria-label="Page navigation example" >
  <ul class="pagination" style="margin-left: 35%;">
  
  <c:if test="${ph.prev }">
  <!-- 이전 -->
    <li class="page-item">
      <a class="page-link" href="/board/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
  </c:if>
  
    <!-- 페이지 번호 -->
    <c:forEach var="i" begin="${ph.startPage }" end="${ph.endPage }">
    <li class="page-item"><a class="page-link" href="/board/list?pageNo=${i }&qty=${ph.pgvo.qty }&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">${i }</a></li>
    </c:forEach>
    
    <c:if test="${ph.next }">
    <!-- 다음 -->
    <li class="page-item">
      <a class="page-link" href="/board/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
    </c:if>
    <li class="page-item"><a class="page-link" href="/board/list"><button class="btn btn-info" style="height: 30px; font-size: 15px; text-align: center;">전체보기</button></a></li>
  </ul>
</nav>

<!-- 검색 라인 -->
<div class="input-group mb-3" style="display: flex; ">
<form action="/board/list" method="get" style="width: 500px;">
	<select name="type" class="form-select form-select-sm" aria-label="Small select example">
		<option selected>고르세요...</option>
		<option ${ph.pgvo.type eq 't' ? 'selected' : ''} value="t">제목</option>
		<option ${ph.pgvo.type eq 'w' ? 'selected' : ''} value="w">작성자</option>
		<option ${ph.pgvo.type eq 'c' ? 'selected' : ''} value="c">내용</option>
		<option ${ph.pgvo.type eq 'tc' ? 'selected' : ''} value="tc">제목&내용</option>
		<option ${ph.pgvo.type eq 'tw' ? 'selected' : ''} value="tw">제목&작성자</option>
		<option ${ph.pgvo.type eq 'wc' ? 'selected' : ''} value="wc">작성자&내용</option>
		<option ${ph.pgvo.type eq 'twc' ? 'selected' : ''} value="twc">제목&작성자&내용</option>
	</select>
	<input class="form-control me-2" type="search" name="keyword" placeholder="검색..." aria-label="Search" value="${ph.pgvo.keyword }">
	<input type="hidden" name="pageNo" value="1">
	<input type="hidden" name="qty" value="10">
     
	<button class="btn btn-outline-success" type="submit" style="margin-top: 10px;">검색
	<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger" >
	${ph.totalCount }
  	</span>
	</button>
</form>
</div>

<jsp:include page="../layout/footer.jsp"/>