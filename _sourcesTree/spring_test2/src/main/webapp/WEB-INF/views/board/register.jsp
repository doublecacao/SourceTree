<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<!DOCTYPE html>

<jsp:include page="../layout/header.jsp"/>

<h2>게시글 작성</h2>
<br>
<form action="/board/register" method="post" enctype="multipart/form-data">

<s:authentication property="principal.mvo.nickName" var="authNick"/>
<s:authentication property="principal.mvo.email" var="authEmail"/>

<div class="mb-3">
  <label for="title" class="form-label">제목</label>
  <input type="text" name="title" class="form-control" id="title" placeholder="Title...">
</div>

<div class="mb-3">
  <label for="writers" class="form-label">작성자</label>
  <input type="text" class="form-control" name="writers" id="writers" value="${authNick }" readonly="readonly"></input>
  <input type="hidden" class="form-control" name="writer" id="writer" value="${authEmail }"></input>
</div>

<div class="mb-3">
  <label for="content" class="form-label">내용</label>
  <textarea class="form-control" name="content" id="content" placeholder="Content..."></textarea>
</div>

<!-- 파일 입력 라인 추가 -->
<div class="mb-3">
  <label for="file" class="form-label">이미지</label>
  <input type="file" class="form-control" name="files" id="files" placeholder="files..." multiple="multiple"></input><br>
 
  <button type="button" class="btn btn-primary" id="trigger">파일업로드</button>
</div>

<!-- 파일 목록 표시라인 -->
<div class="mb-3" id="fileZone">

</div>

<button type="submit" class="btn btn-primary" id="regBtn">작성</button>
</form>

<script src="/resources/js/boardRegister.js"></script>
<jsp:include page="../layout/footer.jsp"/>