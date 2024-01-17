console.log("memberRegister in");

//실행파일, 이미지파일, 파일최대 사이즈 정규 표현식 작성
const regExp = new RegExp("/.(jpg|png|gif|jpeg)$");
const maxSize = 1024*1024*20;   //파일 최대 크기

function fileValidation(fileName, fileSize){
    if(!regExp.test(fileName)){
        return isOk=0;
    }else if(fileSize > maxSize){
        return isOk=0;
    }else{
        return isOk=1;
    }
}

document.addEventListener('change', (e)=>{
    console.log(e.target);
    if(e.target.id=='files'){   //파일에 변화가 생기면
        //input type file element에 저장된 file의 정보를 가져오는 property
        const fileObj = document.getElementById('files').file;
        console.log(fileObj);

        //한번 disabled되면 혼자 풀어질 수 없음. 버튼을 원래 상태로 복구
        document.getElementById('regBtn').disabled = false;

        //첨부파일에 대한 정보를 filezone에 기록
        let div = document.getElementById('fileZone');
        div.innerHTML = ' ';

        // * 여러 파일에 대한 검증을 모두 통과하기 위해서 * 로 각 파일마다 통과 여부를 확인
        let isOk = 1;   //전체 파일 검증 결과
        //개별 파일의 검증 결과
        let validResult = fileValidation(file.name, file.size);
        isOk *= validResult;

        div.innerHTML = fileObj;
    }

    if(isOk == 0){
        //하나의 파일이라도 검증에 통과하지 못한다면 버튼 비활성화
        document.getElementById('regBtn').disabled = true;
    }
})