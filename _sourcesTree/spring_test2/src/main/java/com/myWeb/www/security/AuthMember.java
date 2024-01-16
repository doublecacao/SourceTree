package com.myWeb.www.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
public class AuthMember extends User{
	private static final long serialVersionUID = 1L;
	
	private MemberVO mvo;
	
	//아이디, 패스워드, 유저권한(컬렉션)
	public AuthMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public AuthMember(MemberVO mvo) {
		super(mvo.getEmail(), mvo.getPwd(),
				//list를 map으로 하나씩 보내주고 SimpleGrantedAuthority 객체를 새로 생성하면서 유저의 auth를 컬렉션 형태로 저장
				mvo.getAuthList().stream().map(AuthVO -> new SimpleGrantedAuthority(AuthVO.getAuth()))
				.collect(Collectors.toList()));
		this.mvo = mvo;
	}
}
