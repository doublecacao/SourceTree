package com.myWeb.www.service;


import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myWeb.www.domain.CommentVO;
import com.myWeb.www.domain.PagingVO;
import com.myWeb.www.handler.PagingHandler;
import com.myWeb.www.repository.CommentDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService{
	@Inject
	private CommentDAO cdao;

	@Override
	public int post(CommentVO cvo) {
		log.info("post service impl");
		
		return cdao.post(cvo);
	}

	//Transactional은 totalCount처럼 계속 숫자가 변동될 때 마다 메서드가 다시 실행되지 않도록 락을 걸어주는 역할을 한다
	@Transactional
	@Override
	public PagingHandler spread(long bno, PagingVO pgvo) {
		log.info("spread service impl");
		//ph객체 안에 cmtList 구성
		//totalCount 구해오기
		int totalCount = cdao.selectOneBnoTotalCount(bno);
		List<CommentVO> list = cdao.spread(bno, pgvo);
		PagingHandler ph = new PagingHandler(pgvo, totalCount, list);
		
		return ph;
	}

	@Override
	public int modify(CommentVO cvo) {
		log.info("modify service impl");
		
		return cdao.modify(cvo);
	}

	@Override
	public int delete(long cno) {
		log.info("delete service impl");

		return cdao.delete(cno);
	}
}
