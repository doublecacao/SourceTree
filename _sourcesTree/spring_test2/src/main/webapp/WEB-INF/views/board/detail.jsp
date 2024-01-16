<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<!DOCTYPE html>

<jsp:include page="../layout/header.jsp"/>

<h2>${bdto.bvo.bno }번 게시글</h2>
<br>
<s:authentication property="principal.mvo.email" var="authEmail"/>
<table class="table">
  	<tr>
  		<th scope="row">번호</th>
  		<td>${bdto.bvo.bno }</td>
  	</tr>
  	<tr>
  		<th scope="row">작성자</th>
  		<td>${bdto.bvo.writer }</td>
  	</tr>
  	<tr>
  		<th scope="row">제목</th>
  		<td>${bdto.bvo.title }</td>
  	</tr>
  	<tr>
  		<th scope="row">작성일자</th>
  		<td>${bdto.bvo.regDate }</td>
  	</tr>
  	<tr>
  		<th scope="row">수정일자</th>
  		<td>${bdto.bvo.modDate }</td>
  	</tr>
  	<tr>
  		<th scope="row">조회수</th>
  		<td>${bdto.bvo.readCount }</td>
  	</tr>
  	<tr>
  		<th scope="row">내용</th>
  		<td>${bdto.bvo.content }</td>
  	</tr>
</table>

<!-- 파일 표시 라인 -->
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
							<img alt="" src="/upload/${flist.saveDir }/${flist.uuid}_${flist.fileName}">
							<%-- <img alt="" class="img-fluid" src="/upload/${f:replace(bdto.flist.save_dir, '\\', '/') }/${bdto.flist.uuid}_th_${bdto.flist.file_name}"> --%>
						</div>
					</c:when>
					<c:otherwise>
						<div>
							<!-- 아이콘 같은 모양 하나 가져와서 넣기 -->
							<a href="/upload/${flist.saveDir }/${flist.uuid}_${flist.fileName}" download="${flist.fileName }">
								<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-images" viewBox="0 0 16 16">
							  		<path d="M4.502 9a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3z"/>
							  		<path d="M14.002 13a2 2 0 0 1-2 2h-10a2 2 0 0 1-2-2V5A2 2 0 0 1 2 3a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v8a2 2 0 0 1-1.998 2zM14 2H4a1 1 0 0 0-1 1h9.002a2 2 0 0 1 2 2v7A1 1 0 0 0 15 11V3a1 1 0 0 0-1-1zM2.002 4a1 1 0 0 0-1 1v8l2.646-2.354a.5.5 0 0 1 .63-.062l2.66 1.773 3.71-3.71a.5.5 0 0 1 .577-.094l1.777 1.947V5a1 1 0 0 0-1-1h-10z"/>
							  	</svg>
							</a>
						</div>
					</c:otherwise>
				</c:choose>
				<div>
				<!-- div => 파일이름, 작성일, span size -->
					<div>${flist.fileName }</div>
					${flist.regDate } <br>
					<span class="badge text-bg-warning">${flist.fileSize }Byte</span>
				</div>
			</div>
		</li>	 
	 </c:forEach>
	</ul>
</div>

<c:if test="${authEmail eq bdto.bvo.writer }">
	<a href="/board/modify?bno=${bdto.bvo.bno }"><button type="button" class="btn btn-warning">수정</button></a>
	<a href="/board/delete?bno=${bdto.bvo.bno }"><button type="button" class="btn btn-danger">삭제</button></a>
</c:if>

	<!-- 댓글 등록 라인 -->
	<div class="input-group">
	  <span class="input-group-text" id="cmtWriter">${authEmail }</span>
	  <input type="text" class="form-control" id="cmtText" placeholder="댓글을 등록해주세요..." aria-label="Recipient's username with two button addons">
	  <button class="btn btn-outline-secondary" id="cmtAddBtn" type="button">등록</button>
	</div>
	
  	<!-- 댓글 표시 라인 -->
  	<div class="accordion" id="accordionExample">
	  <div class="accordion-item">
	    <h2 class="accordion-header">
	      <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
	        cno, writer, reg_date
	      </button>
	    </h2>
	    <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">
	      <div class="accordion-body">
	      <strong>댓글을 추가해주세요...</strong>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 더보기 버튼 -->
	<div>
		<button type="button" id="moreBtn" data-page="1" class="btn btn-outline-dark" style="visibility: hidden;">MORE +</button>
	</div>
	<!-- 모달창 라인 -->
	<div class="modal" id="myModal" tabindex="-1">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Writer</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
		      <div class="input-group mb-3">
		      	<input type="text" class="form-control" id="cmtModText" placeholder="댓글을 등록해주세요..." aria-label="Recipient's username with two button addons">
	        	<button type="button" id="cmtModBtn" class="btn btn-primary"  >등록</button>
      		  </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	      </div>
	    </div>
	  </div>
	</div>
	
<script type="text/javascript">
let bnoVal = `<c:out value="${bdto.bvo.bno}"/>`;
console.log(bnoVal);
</script>
<script src="/resources/js/boardComment.js"></script>
<script type="text/javascript">
spreadCommentList(bnoVal);
</script>
<jsp:include page="../layout/footer.jsp"/>