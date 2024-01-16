console.log("boardModify in");

document.addEventListener('click', (e)=>{
    if(e.target.classList.contains('file-x')){
        let uuid = e.target.dataset.uuid;   //data-uuid를 가져온다
        console.log(uuid);
        removeFileToServer(uuid).then(result => {
            console.log(result);
            if(result === "1"){
                alert("파일 삭제 완료");
                e.target.closest('li').remove();
            }else{
                alert("파일 삭제 실패");
            }
        })
    }
})

async function removeFileToServer(uuid){
    try {
        const url = "/board/remove/"+uuid;
        const config = {
            method:"delete"
        }
        const resp = await fetch(url, config);
        const result = await resp.text();

        return result;
    } catch (error) {
        console.log(error);
    }
}