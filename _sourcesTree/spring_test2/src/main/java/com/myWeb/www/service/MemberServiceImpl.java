package com.myWeb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.myWeb.www.repository.MemberDAO;
import com.myWeb.www.security.AuthVO;
import com.myWeb.www.security.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{
	
	@Inject
	private MemberDAO mdao;
	
	@Override
	public int memberRegister(MemberVO mvo) {
		log.info("register service impl");
		mdao.memberRegister(mvo);
		
		return mdao.registerAuthInit(mvo.getEmail());
	}

	@Override
	public boolean updateLastLogin(String authEmail) {
		log.info("updateLastLogin service impl");
		
		return mdao.updateLastLogin(authEmail) > 0 ? true : false;
	}

	@Override
	public MemberVO detail(String email) {
		log.info("detail service impl");
		
		return mdao.detail(email);
	}

	@Override
	public int modify(MemberVO mvo) {
		log.info("modify service impl");
		
		return mdao.modify(mvo);
	}

	@Override
	public List<AuthVO> selectAuth(String email) {
		log.info("selectAuth service impl");
		
		return mdao.selectAuth(email);
	}

	@Override
	public List<MemberVO> mvoList() {
		log.info("mvoList service impl");
		
		return mdao.mvoList();
	}

	@Override
	public int delete(String email) {
		log.info("delete service impl");
		mdao.deleteAuth(email);
		
		return mdao.delete(email);
	}

}
