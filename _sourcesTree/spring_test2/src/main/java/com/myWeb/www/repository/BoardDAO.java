package com.myWeb.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	List<BoardVO> userList(@Param("writer") String writer, @Param("pgvo") PagingVO pgvo);

	int getUserCount(String writer);

}
