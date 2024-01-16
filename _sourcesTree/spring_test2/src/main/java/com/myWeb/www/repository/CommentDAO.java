package com.myWeb.www.repository;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myWeb.www.domain.CommentVO;
import com.myWeb.www.domain.PagingVO;
import com.myWeb.www.handler.PagingHandler;

public interface CommentDAO {

	int post(CommentVO cvo);
	
//매개변수가 2개 이상일 때 @Param을 이용한다
	List<CommentVO> spread(@Param("bno") long bno, @Param("pgvo") PagingVO pgvo);

	int selectOneBnoTotalCount(long bno);

	int modify(CommentVO cvo);

	int delete(long cno);

}
