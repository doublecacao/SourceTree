package com.myWeb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.myWeb.www.domain.BoardDTO;
import com.myWeb.www.domain.BoardVO;
import com.myWeb.www.domain.FileVO;
import com.myWeb.www.domain.PagingVO;
import com.myWeb.www.handler.PagingHandler;
import com.myWeb.www.repository.BoardDAO;
import com.myWeb.www.repository.CommentDAO;
import com.myWeb.www.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
	@Inject
	private BoardDAO bdao;
	
	@Inject
	private FileDAO fdao;

	@Override
	public int boardRegister(BoardDTO bdto) {
		log.info("register service impl");
		//bvo boardMapper / flist fileMapper 등록
		int isOk = bdao.boardRegister(bdto.getBvo());
		
		if(bdto.getFlist() == null) {
			return isOk;
		}
		
		//bvo Register 후 파일도 있다면
		if(isOk > 0 && bdto.getFlist().size() > 0) {
			//bno settiong
			long bno = bdao.selectOneBno();	//가장 마지막에 등록된 bno
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isOk *= fdao.registerFile(fvo);
			}
		}
		return isOk; 
	}

	@Override
	public List<BoardVO> boardList(PagingVO pgvo) {
		log.info("list service impl");
		
		return bdao.boardList(pgvo);
	}

	@Override
	public BoardVO boardDetail(int bno) {
		log.info("detail service impl");
		
		return bdao.boardDetail(bno);
	}

	@Override
	public int boardDelete(int bno) {
		log.info("delete service impl");
		
		fdao.fileDelete(bno);
		return bdao.boardDelete(bno);
	}

	@Override
	public int boardModify(BoardDTO bdto) {
		log.info("modify service impl");

		int isOk = bdao.boardModify(bdto.getBvo());
		
		if(bdto.getFlist() == null) {
			return isOk;
		}
		
		if(isOk > 0 && bdto.getFlist().size() > 0) {
			//bno settiong
			long bno = bdto.getBvo().getBno();
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isOk *= fdao.registerFile(fvo);
			}
		}
		return isOk;
	}

	@Override
	public int readCountIncrease(int bno) {
		log.info("readCount service impl");
		
		return bdao.readCountIncrease(bno);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		log.info("totalCount service impl");
		
		return bdao.getTotalCount(pgvo);
	}

	@Override
	public List<FileVO> fileList(int bno) {
		log.info("fileList service impl");
		
		return fdao.fileList(bno);
	}

	@Override
	public int remove(String uuid) {
		log.info("remove service impl");
		
		return fdao.remove(uuid);
	}

	@Override
	public void updateCommentQty(PagingHandler ph) {
		log.info("updateCommentQty service impl");
		
		bdao.updateCommentQty(ph);
	}

	@Override
	public void updateFileQty(PagingHandler ph) {
		log.info("updateFileQty service impl");
		
		bdao.updateFileQty(ph);
	}

}
