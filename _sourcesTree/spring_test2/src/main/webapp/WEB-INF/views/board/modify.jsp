<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<jsp:include page="../layout/header.jsp"/>

<h2>게시글 수정</h2>
<br>
<form action="/board/modify" method="post" enctype="multipart/form-data">

<input type="hidden" name="bno" id="bno" value="${bdto.bvo.bno }">

<div class="mb-3">
  <label for="title" class="form-label">제목</label>
  <input type="text" name="title" class="form-control" id="title" value="${bdto.bvo.title }">
</div>

<div class="mb-3">
  <label for="writer" class="form-label">작성자</label>
  <input type="text" class="form-control" name="writer" id="writer" value="${bdto.bvo.writer }" readonly></input>
</div>

<div class="mb-3">
  <label for="content" class="form-label">내용</label>
  <textarea class="form-control" name="content" id="content">${bdto.bvo.content }</textarea>
</div>

<div>
	<ul>
	<!-- 파일 개수만큼 li를 추가하여 파일을 표시, 타입이 1인 경우만 표시 -->
	<!-- 
		li -> div => img
	 -->
	 <!-- 파일 리스트 중 하나만 가져와서 fvo로 저장 -->
	 <c:forEach items="${bdto.flist }" var="flist">
		<li class="list-group-flush" style="list-style: none;">
			<div class="col-12">
				<c:choose>
					<c:when test="${flist.fileType eq 1 }">
						<div>
						<!-- /upload/save_dir/(여기까지 경로)uuid_file_name(파일명) -->
							<img alt="" src="/upload/${flist.saveDir }/${flist.uuid}_th_${flist.fileName}">
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
				<div>
				<!-- div => 파일이름, 작성일, span size -->
					<div>${flist.fileName }</div>
					${flist.regDate } <br>
					<span class="badge text-bg-warning" style="margin-bottom: 25px;">${flist.fileSize }Byte</span>
					<button type="button" data-uuid="${flist.uuid }" class="btn btn-sm btn-outline-danger file-x">X</button>
					<hr>
				</div>
			</div>
		</li>	 
	 </c:forEach>
	</ul>
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

<button type="submit" class="btn btn-warning" id="regBtn">수정</button>
<a href="/board/detail?bno=${bdto.bvo.bno }"><button type="button" class="btn btn-danger">취소</button></a>
</form>

<script src="/resources/js/boardRegister.js"></script>
<script src="/resources/js/boardModify.js"></script>
<jsp:include page="../layout/footer.jsp"/>