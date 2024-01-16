package com.myWeb.www.repository;

import java.util.List;

import com.myWeb.www.domain.BoardVO;
import com.myWeb.www.domain.PagingVO;
import com.myWeb.www.handler.PagingHandler;

public interface BoardDAO {

	int boardRegister(BoardVO boardVO);

	List<BoardVO> boardList(PagingVO pgvo);

	BoardVO boardDetail(int bno);

	int boardDelete(int bno);

	int boardModify(BoardVO bvo);

	int readCountIncrease(int bno);

	int getTotalCount(PagingVO pgvo);

	long selectOneBno();

	void updateCommentQty(PagingHandler ph);

	void updateFileQty(PagingHandler ph);

}
