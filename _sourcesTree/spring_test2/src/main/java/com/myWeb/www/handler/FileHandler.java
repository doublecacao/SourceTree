package com.myWeb.www.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.myWeb.www.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component	//사용자 클래스를 bean으로 등록하는 어노테이션
public class FileHandler {
	private final String UP_DIR = "D:\\SONG\\_myProject\\_java\\_fileUpload";
	
	public List<FileVO> uploadFiles(MultipartFile[] files){
		List<FileVO> flist = new ArrayList<>();
		//FileVO 생성, 파일을 경로에 맞춰서 저장, 썸네일 저장
		//날짜를 폴더로 생성하여 그날그날 업로드 파일을 관리
		String date = LocalDate.now().toString();	// "2024-01-10"
		date = date.replace("-", File.separator);	//	\(win)	/(mac)
		
		//D:\\SONG\\_myProject\\_java\\_fileUpload\\2024\\01\\10
		File folders = new File(UP_DIR, date);	//File객체는 처음부터 모든 경로가 필요하다
		
		//폴더 생성	exists : 유무 여부 확인
		if(!folders.exists()) {
			folders.mkdirs();		//mkdir() : 하나의 폴더만 생성 / mkdirs() : 여러개의 폴더를 동시에 생성
		}
		
		//files 객체에 대한 설정
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			fvo.setSaveDir(date);
			fvo.setFileSize(file.getSize());
			
			String originalFileName = file.getOriginalFilename();
			String fileName = originalFileName.substring(originalFileName.lastIndexOf(File.separator)+1);
			log.info("fileName >>> {}", fileName);
			fvo.setFileName(fileName);
			
			String uuid = UUID.randomUUID().toString();
			fvo.setUuid(uuid);
			// -------------------- 기본 fvo setting 완료
			
			//디스크에 저장할 파일 객체를 생성
			String fullFileName = uuid+"_"+fileName;
			File storeFile = new File(folders, fullFileName);
			//실제 파일이 저장되려면 첫 경로부터 다 설정되어 있어야 한다
			//D:\\SONG\\_myProject\\_java\\_fileUpload\\2024\\01\\10\\uuid_fileName.jpg
			
			try {
				file.transferTo(storeFile);
				//썸네일 생성 => 이미지 파일만 썸네일 생성 가능
				//이미지인지 확인
				if(isImageFile(storeFile)) {
					fvo.setFileType(1);	//이미지파일은 타입이 1
					//썸네일 생성
					File thumbNail = new File(folders, uuid+"_th_"+fileName);
					Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("파일 생성 오류");
			}
			
			//list에 fvo 추가
			flist.add(fvo);
		}
		
		return flist;
	}
	
	//이미지인지 확인하는 메서드 생성
	private boolean isImageFile(File storeFile) throws IOException {
		String mimeType = new Tika().detect(storeFile);	//type image/jpg
		
		return mimeType.startsWith("image") ? true : false;
	}
}
