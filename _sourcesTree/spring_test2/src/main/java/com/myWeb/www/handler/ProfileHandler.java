package com.myWeb.www.handler;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.myWeb.www.domain.FileVO;
import com.myWeb.www.security.MemberVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component
public class ProfileHandler {
	private final String UP_DIR = "D:\\SONG\\_myProject\\_java\\_fileUpload\\_profile";
	
	public FileVO uploadProfile(MultipartFile file, MemberVO mvo){
		//FileVO 생성, 파일을 경로에 맞춰서 저장, 썸네일 저장
		//날짜를 폴더로 생성하여 그날그날 업로드 파일을 관리
		String id = mvo.getEmail();
		
		//D:\\SONG\\_myProject\\_java\\_fileUpload\\email
		File folder = new File(UP_DIR, id);	//File객체는 처음부터 모든 경로가 필요하다
		
		//폴더 생성	exists : 유무 여부 확인
		if(!folder.exists()) {
			folder.mkdir();		//mkdir() : 하나의 폴더만 생성 / mkdirs() : 여러개의 폴더를 동시에 생성
		}
		
		//files 객체에 대한 설정

			FileVO fvo = new FileVO();
			String originalFileName = file.getOriginalFilename();
			fvo.setFileExtention(originalFileName.substring(originalFileName.lastIndexOf(".")));
			fvo.setSaveDir("_profile"+File.separator+id);
			fvo.setFileSize(file.getSize());
			fvo.setFileName(id);
			
			String uuid = UUID.randomUUID().toString();
			fvo.setUuid(uuid);
			// -------------------- 기본 fvo setting 완료
			
			File storeFile = new File(folder, fvo.getUuid()+fvo.getFileName()+fvo.getFileExtention());
			//실제 파일이 저장되려면 첫 경로부터 다 설정되어 있어야 한다
			//D:\\SONG\\_myProject\\_java\\_fileUpload\\email\\uuid_fileName.jpg
			
			try {
				file.transferTo(storeFile);
				//썸네일 생성 => 이미지 파일만 썸네일 생성 가능
				//이미지인지 확인
				if(isImageFile(storeFile)) {
					fvo.setFileType(1);	//이미지파일은 타입이 1
					//썸네일 생성
					File profile = new File(folder, uuid+"_profile_"+fvo.getFileName()+fvo.getFileExtention());
					Thumbnails.of(storeFile).size(300, 300).toFile(profile);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("파일 생성 오류");
			}
			
		return fvo;
	}
	
	public int fileRemover(String DEL_DIR) {
		String path = UP_DIR+"\\"+DEL_DIR;	//_profile+\\+email
		File deleteFile = new File(path);
		
		if(deleteFile.exists()) {	//파일 유/무 확인
			File[] deleteFileList = deleteFile.listFiles();	//파일 배열화
			
			for(File deleteFiles : deleteFileList) {
				deleteFiles.delete();	//해당 폴더 내부 파일 지우기
			}
				deleteFile.delete();		//해당 아이디 폴더 삭제
		}
		return 1;						//성공/실패 리턴
	}
	
	//이미지인지 확인하는 메서드 생성
	private boolean isImageFile(File storeFile) throws IOException {
		String mimeType = new Tika().detect(storeFile);	//type image/jpg
		
		return mimeType.startsWith("image") ? true : false;
	}
}
