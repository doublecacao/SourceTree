package com.myWeb.www.service;

import java.util.List;

import com.myWeb.www.domain.FileVO;
import com.myWeb.www.domain.MemberDTO;
import com.myWeb.www.security.AuthVO;
import com.myWeb.www.security.MemberVO;

public interface MemberService {

	int memberRegister(MemberDTO memberDTO);

	boolean updateLastLogin(String authEmail);

	MemberVO detail(String email);

	int modify(MemberDTO mdto);

	List<AuthVO> selectAuth(String email);

	List<MemberVO> mvoList();

	int delete(String email);

	FileVO getFile(String email);

	String getFileDir(String email);
}
