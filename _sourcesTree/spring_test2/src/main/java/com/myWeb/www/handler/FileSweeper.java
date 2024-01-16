package com.myWeb.www.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myWeb.www.domain.FileVO;
import com.myWeb.www.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class FileSweeper {
	private final String BASE_PATH = "D:\\SONG\\_myProject\\_java\\_fileUpload\\";
	
	@Inject
	private FileDAO fdao;
	
	//초 분 시 일 월 요일 년도(생략가능)
	@Scheduled(cron="0 0 0 * * *")
	public void fileSweeper() {
		log.info("FileSweeper Running Start >>> {}", LocalDateTime.now());
		
		//DB에 등록된 파일 목록 가져오기
		List<FileVO> fList = fdao.selectList();
		
		//저장소를 검색할 때 필요한 파일의 경로 리스트(실제 존재해야 될 리스트)
		List<String> currentFiles = new ArrayList<String>();
		
		for(FileVO fvo : fList) {
			String filePath = fvo.getSaveDir()+File.separator+fvo.getUuid();
			String fileName = fvo.getFileName();
			currentFiles.add(BASE_PATH+filePath+"_"+fileName);
			
			//이미지라면 썸네일 경로도 추가
			if(fvo.getFileType()>0) {
				currentFiles.add(BASE_PATH+filePath+"_th_"+fileName);
			}
		}
		
		log.info("currentFiles >>> {}", currentFiles);
		
		//오늘 날짜를 반영한 폴더 구조 경로 만들기
		String today = LocalDate.now().toString();	// 2024-01-12
		today = today.replace("-", File.separator);
		
		//경로를 기반으로 저장되어있는 파일을 검색 (오늘 날짜 폴더 안에 있는 전체 파일)
		File dir = Paths.get(BASE_PATH+today).toFile();
		File[] fileObjects = dir.listFiles();
		
		//실제 저장되어있는 파일과 DB에 존재하는 파일을 비교하여 없는 파일은 삭제 진행
		for(File file : fileObjects) {
			String storedFileName = file.toPath().toString();
			if(!currentFiles.contains(storedFileName)) {
				file.delete();	//폴더에서 파일 삭제
				log.info("delete File >>> {}", storedFileName);
			}
		}
		
		log.info("FileSweeper Running Finish >>> {}", LocalDateTime.now());
	}
}
