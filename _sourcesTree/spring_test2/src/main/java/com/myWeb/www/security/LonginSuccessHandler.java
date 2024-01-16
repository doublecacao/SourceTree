package com.myWeb.www.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.myWeb.www.service.MemberService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

//내가 만든 객체를 Inject 하기 위해선 Component 어노테이션이 필요하다
@Slf4j
@Component
public class LonginSuccessHandler implements AuthenticationSuccessHandler {

	@Setter
	@Getter
	private String authEmail;
	
	@Setter
	@Getter	
	private String authUrl;
	
	//redirect된 데이터를 가지고 리다이텍스 하는 역할
	private RedirectStrategy redStrategy = new DefaultRedirectStrategy();
	//실제 로그인 정보, 경로 등을 캐쉬로 저장
	private RequestCache reqCache = new HttpSessionRequestCache();
	
	@Inject
	private MemberService msv;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {	//authentication은 CustomAuthMemberService에서 인증에 성공된 객체
		setAuthEmail(authentication.getName());	//인증된 객체의 이메일
		setAuthUrl("/");					//로그인 성공 후 보낼 URL

		boolean isOk = msv.updateLastLogin(getAuthEmail());
		
		//내부에서 로그인 세션 저장
		HttpSession ses = request.getSession();
		log.info("loginSession >>> {}", ses.toString());
		
		if(!isOk || ses == null) {
			return;
		}else {
			//시큐리티에서 로그인을 시도하면 시도한 로그인 기록이 남게되기 때문에
			//이전에 시도한 시큐리티의 인증 실패 기록 삭제
			ses.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		//기존 객체 저장
		SavedRequest saveReq = reqCache.getRequest(request, response);	//이전에 요청했던 경로들
		//로그인 시, 원래 위치하던 경로가 있었다면 그 위치의 url로 이동시켜주고 없었다면 기존 authUrl로 리다이렉트 시켜준다
		redStrategy.sendRedirect(request, response, (saveReq != null ? saveReq.getRedirectUrl() : authUrl));
	}

}
