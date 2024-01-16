package com.myWeb.www.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {RootConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {ServletConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	//EncodingFilter 설정
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encoding = new CharacterEncodingFilter();
		encoding.setEncoding("UTF-8");
		encoding.setForceEncoding(true);	//외부로 나가는 데이터 인코딩 설정(response)
		
		return new Filter[] {encoding};
	}

	//기타 사용자 설정
	@Override
	protected void customizeRegistration(Dynamic registration) {
		//multipartConfig 설정
		//사용자 지정 익셉션 처리 지정
		
		//파일 업로드 설정
		String uploadLocation = "D:\\SONG\\_myProject\\_java\\_fileUpload";
		int maxFileSize = 1024*1024*20;	//20M
		int maxReqSize = maxFileSize*2;	//40M
		int fileSizeThreshold = maxFileSize;	//20M
		
		//multipartConfig 설정
		MultipartConfigElement multipartConfig = 
				new MultipartConfigElement(uploadLocation, maxFileSize, maxReqSize, fileSizeThreshold);
		registration.setMultipartConfig(multipartConfig);
	}

}
