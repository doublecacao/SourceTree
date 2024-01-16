package com.myWeb.www.repository;

import java.util.List;

import com.myWeb.www.security.AuthVO;
import com.myWeb.www.security.MemberVO;

public interface MemberDAO {

	int memberRegister(MemberVO mvo);

	int registerAuthInit(String email);

	MemberVO selectEmail(String username);

	List<AuthVO> selectAuth(String username);

	int updateLastLogin(String authEmail);

	MemberVO detail(String name);

	int modify(MemberVO mvo);

	List<MemberVO> mvoList();

	int delete(String email);

	void deleteAuth(String email);

}
