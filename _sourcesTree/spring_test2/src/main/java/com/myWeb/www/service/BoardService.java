package com.myWeb.www.service;

import java.util.List;

import com.myWeb.www.domain.BoardDTO;
import com.myWeb.www.domain.BoardVO;
import com.myWeb.www.domain.FileVO;
import com.myWeb.www.domain.PagingVO;
import com.myWeb.www.handler.PagingHandler;

public interface BoardService {

	int boardRegister(BoardDTO boardDTO);

	List<BoardVO> boardList(PagingVO pgvo);

	BoardVO boardDetail(int bno);

	int boardDelete(int bno);

	int boardModify(BoardDTO bdto);

	int readCountIncrease(int bno);

	int getTotalCount(PagingVO pgvo);

	List<FileVO> fileList(int bno);

	int remove(String uuid);

	void updateCommentQty(PagingHandler ph);

	void updateFileQty(PagingHandler ph);

}
