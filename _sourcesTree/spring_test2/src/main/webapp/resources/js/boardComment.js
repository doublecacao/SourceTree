console.log("boardComment.js In.");

document.getElementById("cmtAddBtn").addEventListener('click', ()=>{
    const cmtText = document.getElementById('cmtText');

    if(cmtText.value == null || cmtText.value == ''){
        alert('댓글을 입력해주세요.');
        cmtText.focus();

        return false;
    }else{
        let cmtData={
            bno: bnoVal,
            writer: document.getElementById('cmtWriter').innerText,
            content: cmtText.value
        };
        console.log(cmtData);

        postCommentToServer(cmtData).then(result => {
            if(result === '1'){
                alert("댓글 등록 성공");
                cmtText.value="";
            }else{
                alert("댓글 등록 실패");
            }
            //화면에 뿌리기
            spreadCommentList(bnoVal);
        });
    }
});

async function postCommentToServer(cmtData){
    try {
        const url = "/comment/post";
        const config={
            method: "post",
            headers:{
                'content-type':'application/json; charset=UTF-8'
            },
            body: JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();

        return result;
    } catch (error) {
        console.log(error);
    }
};

async function getCommentListFromServer(bno, page){
    try {
        const resp = await fetch("/comment/"+bno+"/"+page);
        const result = await resp.json();

        return result;
    } catch (error) {
        console.log(error);
    }
}

function spreadCommentList(bno, page=1){
    getCommentListFromServer(bno, page).then(result => {
        console.log(result);
        const div = document.getElementById("accordionExample");

        if(page == 1){
            div.innerHTML = '';
        }

        if(result.cmtList.length > 0){
            for(let i=0; i<result.cmtList.length; i++){
                let add = `<div class="accordion-item">`;
                add += `<h2 class="accordion-header">`;
                add += `<div class="accordion-item">`;
                add += `<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${i}" aria-expanded="true" aria-controls="collapse${i}">`;
                add += `번호 : ${result.cmtList[i].cno}, 작성자 : ${result.cmtList[i].writer}, 작성일 : ${result.cmtList[i].regDate}`;
                add += `</button>`;
                add += `</h2>`;
	            add += `<div id="collapse${i}" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">`;
	            add += `<div class="accordion-body">`;
                add += `<input type="text" class="form-control cmtText" value="${result.cmtList[i].content}"readonly="readonly">`;
                add += `<c:if test="${authEmail eq bdto.bvo.writer }">`;
                add += `<button type="button" data-cno="${result.cmtList[i].cno}" data-writer="${result.cmtList[i].writer}" class="btn btn-outline-warning  btn-sm cmtModBtn" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`;
                add += `<button type="button" data-cno="${result.cmtList[i].cno}" data-writer="${result.cmtList[i].writer}" class="btn btn-outline-danger  btn-sm cmtDelBtn">삭제</button>`;
                add += `</c:if>`;
                add += `</div>`;
                add += `</div>`;
                add += `</div>`;

                div.innerHTML += add;
            }

            let moreBtn = document.getElementById('moreBtn');
            console.log(moreBtn);

            if(result.pgvo.pageNo < result.endPage){
                moreBtn.style.visibility = 'visible';   //버튼 표시
                moreBtn.dataset.page = page+1;
            }else{
                moreBtn.style.visibility = 'hidden';   //버튼 표시
            }
        }else{
            div.innerHTML = `<div class="accordion-body">댓글이 비어있습니다.</div>`;
        }
    })
}

document.addEventListener('click', (e)=>{
    console.log(e.target);
   
    if(e.target.id == 'moreBtn'){
        let page = parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal, page);
    }
    if(e.target.classList.contains('cmtModBtn')){
        // nextSibling : 한 부모 안에서 같은(다음) 형제를 찾기
        //모달창에 기존 댓글 내용을 반영 (수정하기 편하게)
        let div = e.target.closest('div');  //가장 가까운 div
        let cmtText = div.querySelector(".cmtText").value;  //그 div의 cmtText클래스를 가진 태그의 값
        console.log(cmtText);
        document.getElementById('cmtModText').value = cmtText;  //입력창이(input) 아닌 구조체는(li, div...) .nodeValue를 이용해 값을 넣을 수 있다

        //수정 => cno, writer, content를 위한
        document.getElementById('cmtModBtn').setAttribute("data-cno", e.target.dataset.cno);
        document.getElementById('cmtModBtn').setAttribute("data-writer", e.target.dataset.writer);
    }else if(e.target.id == 'cmtModBtn'){
        let cmtData = {
            cno: e.target.dataset.cno,
            writer: e.target.dataset.writer,
            content: document.getElementById('cmtModText').value
        };
        console.log(cmtData);

        updateCommentToServer(cmtData).then(result => {
            if(result === '1'){
                //모달창 닫기(.btn-close를 클릭한 것과 같은 효과)
                document.querySelector('.btn-close').click();
                
                alert("댓글 수정 완료");
                spreadCommentList(bnoVal);
            }else{
                alert("댓글 수정 실패");
                document.querySelector('.btn-close').click();
            }
        })
    }else if(e.target.classList.contains('cmtDelBtn')){
        deleteCommentToServer(e.target.dataset.cno).then(result => {
            if(result === '1'){
                alert("댓글 삭제 완료");
                spreadCommentList(bnoVal);
            }else{
                alert("댓글 삭제 실패");
            }
        })
    }
})

async function updateCommentToServer(cmtData){
    try {
        const url = '/comment/modify';
        const config={
            method:'put',
            headers:{
                'content-type':'application/json; charset=UTF-8'
            },
            body: JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = resp.text();

        return result;
    } catch (error) {
        console.log(error);
    }
}

async function deleteCommentToServer(cnoVal){
    console.log(cnoVal);
    try {
        const url = '/comment/delete/'+cnoVal;
        const config={
            method:'delete'
        };
        const resp = await fetch(url, config);
        const result = resp.text();

        return result;
    } catch (error) {
        console.log(error);
    }
}