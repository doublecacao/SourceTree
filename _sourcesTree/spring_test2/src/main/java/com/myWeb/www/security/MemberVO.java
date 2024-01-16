package com.myWeb.www.security;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class MemberVO {
	private String email;
	private String pwd;
	private String nickName; 
	private String regDate;
	private String lastLogin;
	private List<AuthVO> authList;
}
