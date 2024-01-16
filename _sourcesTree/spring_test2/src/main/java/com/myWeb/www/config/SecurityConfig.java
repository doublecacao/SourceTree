package com.myWeb.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.myWeb.www.security.CustomAuthMemberService;
import com.myWeb.www.security.LoginFailureHandler;
import com.myWeb.www.security.LonginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
//WebConfig에 SecurityConfig.class 등록*
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	//비밀번호 암호화 객체 PasswordEncoder
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//SuccessHandler
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler(){
		return new LonginSuccessHandler();
	}
	
	//FailureHandler
	@Bean
	public AuthenticationFailureHandler authFailireHandler() {
		return new LoginFailureHandler();
	}
	
	//UserDetail
	@Bean
	public UserDetailsService customUserService() {
		return new CustomAuthMemberService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//인증되는 객체 설정
		auth.userDetailsService(customUserService()).passwordEncoder(bcPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//화면에서 설정되는 권한에 따른 주소 맵핑 설정
		
		//csrf() 공격에 대한 설정 막기
		http.csrf().disable();
		
		//승인 요청
		//antMatchers : 접근을 허용하는 경로
		//ADMIN만이 사용 할 수 있는 경로를 antMatchers에 , 를 붙혀서 계속 적어주면 된다
		http.authorizeRequests().antMatchers("/member/list").hasRole("ADMIN")
		//permitAll() : 누구나 접근 가능한 경로 /** 이면 대상 경로에 해당하는 모든 것을 의미
		//resources와 같이 스크립트파일이 있는 경우에는 그 경로는 반드시 명시
		.antMatchers("/", "/board/list", "/board/detail", "/comment/**", "/resources/**", "/member/register", "/member/login").permitAll()
		//나머지 요청들은 인증된(로그인 한) 사용자만 허용
		.anyRequest().authenticated();
		
		//커스텀 로그인 페이지 구성
		//Controller에 주소요청 맵핑이 같이 있어야 한다(필수)
		http.formLogin()
		//usernameParameter(아이디 역할 하는 것)
		.usernameParameter("email")
		//passwordParameter(비밀번호 역할 하는 것)
		.passwordParameter("pwd")
		//loginPage(로그인 페이지 경로)
		.loginPage("/member/login")
		//성공시(사용자 지정 메서드)
		.successHandler(authSuccessHandler())
		//실패시(사용자 지정 메서드)
		.failureHandler(authFailireHandler());
		
		//로그아웃 페이지
		//method = "post" (필수)
		http.logout()
		//logoutUrl(로그아웃 경로)
		.logoutUrl("/member/logout")
		//세션끊기
		.invalidateHttpSession(true)
		//로그인 정보(쿠키) 삭제
		.deleteCookies("JSESSIONID")
		//로그아웃 성공시 경로
		.logoutSuccessUrl("/");
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return super.userDetailsService();
	}
	
}
