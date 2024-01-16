package com.myWeb.www.service;

import java.util.Collection;
import java.util.List;

import com.myWeb.www.security.AuthVO;
import com.myWeb.www.security.MemberVO;

public interface MemberService {

	int memberRegister(MemberVO mvo);

	boolean updateLastLogin(String authEmail);

	MemberVO detail(String email);

	int modify(MemberVO mvo);

	List<AuthVO> selectAuth(String email);

	List<MemberVO> mvoList();

	int delete(String email);
}