package com.myWeb.www.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
	private String authEmail;
	private String errorMsg;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		setAuthEmail(request.getParameter("email"));	//로그인 실패한 이메일을 수집
		
		//exception 발생시, 에러메시지 저장
		//아이디 또는 비밀번호가 일치하지 않을 때 던지는 예외 || 사용자 정보를 검색하거나 유효성을 확인하는 서비스에서 오류
		if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
			setErrorMsg(exception.getMessage().toString());
		}
		log.info("errorMsg >>> {}", errorMsg);
		
		request.setAttribute("email", getAuthEmail());
		request.setAttribute("errorMsg", getErrorMsg());
//		에러메시지 서브쿼리로 해당 경로로 달고가기
		request.getRequestDispatcher("/member/login?error").forward(request, response);
	}

}
