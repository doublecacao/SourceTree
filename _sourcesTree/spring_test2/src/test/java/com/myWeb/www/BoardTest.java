package com.myWeb.www;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myWeb.www.domain.BoardVO;
import com.myWeb.www.repository.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.myWeb.www.config.RootConfig.class})	//classes 경로에 있는 대상자의 설정을 가져오는 어노테이션
public class BoardTest {
	@Inject
	private BoardDAO bdao;
	
	@Test
	public void insertBoard() {
		for(int i=0; i<300; i++) {
			BoardVO bvo = new BoardVO();
			bvo.setTitle("Test Title "+i);
			bvo.setWriter("Tester");
			bvo.setContent("Test Content "+i+"입니다");
			bdao.boardRegister(bvo);
		}
	}
}
