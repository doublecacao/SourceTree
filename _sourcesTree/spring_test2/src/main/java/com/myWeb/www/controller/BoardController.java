package com.myWeb.www.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myWeb.www.domain.BoardDTO;
import com.myWeb.www.domain.BoardVO;
import com.myWeb.www.domain.FileVO;
import com.myWeb.www.domain.PagingVO;
import com.myWeb.www.handler.FileHandler;
import com.myWeb.www.handler.PagingHandler;
import com.myWeb.www.service.BoardService;

import lombok.extern.slf4j.Slf4j;

//@RequiredArgsConstructor 선언 후 private final로 객체 등록 => 생성자 주입

@Slf4j
@RequestMapping("/board/*")
@Controller
public class BoardController {
	@Inject
	private BoardService bsv;
	
	@Inject
	private FileHandler fh;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String boardRegister(BoardVO bvo, @RequestParam(name="files", required = false) MultipartFile[] files) {
		log.info("register bvo >>> {}", bvo);
		List<FileVO> flist = null;
		
		//fileHandler 생성	multipartFile -> flist
		if(files[0].getSize() > 0) {
			flist = fh.uploadFiles(files);
		}
		
		int isOk = bsv.boardRegister(new BoardDTO(bvo, flist));
		
		log.info("boardRegister >>> {}", isOk>0?"성공":"실패");
		
		return "index";
	}
	
	@GetMapping("/list")
	public String boardList(Model m, PagingVO pgvo) {
		log.info("boardList in");
		
		//페이징 처리
		int totalCount = bsv.getTotalCount(pgvo);
		PagingHandler ph = new PagingHandler(pgvo, totalCount);
		
		//게시글당 파일과 댓글 개수 업데이트
		bsv.updateCommentQty(ph);
		bsv.updateFileQty(ph);
		
		//totalCount 구하기
		log.info("PagingVO >>> {}", pgvo);
		List<BoardVO> list = bsv.boardList(pgvo);
		
		m.addAttribute("ph", ph);
		m.addAttribute("list", list);
		
		return "/board/list";
	}
	
	@Transactional
	@GetMapping("/detail")
	public void boardDetail(Model m, @RequestParam("bno") int bno) {
		log.info("boardDetailBno >>> {}", bno);
		
		bsv.readCountIncrease(bno);
		
		BoardVO bvo = bsv.boardDetail(bno);
		List<FileVO> flist = new ArrayList<FileVO>(bsv.fileList(bno));
		
		BoardDTO bdto = new BoardDTO(bvo, flist);

		log.info("detailBdto >>> {}", bdto);
		
		m.addAttribute("bdto", bdto);
	}
	
	@Transactional
	@GetMapping("/modify")
	public void boardDetail2(Model m, @RequestParam("bno") int bno) {
		log.info("boardDetailBno >>> {}", bno);
		
		BoardVO bvo = bsv.boardDetail(bno);
		List<FileVO> flist = new ArrayList<FileVO>(bsv.fileList(bno));
		
		BoardDTO bdto = new BoardDTO(bvo, flist);

		log.info("detailBdto >>> {}", bdto);
		
		m.addAttribute("bdto", bdto);
	}
	
	@GetMapping("/delete")
	public String boardDelete(@RequestParam("bno") int bno) {
		log.info("boardDeleteBno >>> {}", bno);
		
		int isOk = bsv.boardDelete(bno);
		
		log.info("boardDelete >>> {}", isOk>0?"성공":"실패");
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/modify")
	public String boardModify(BoardVO bvo, @RequestParam(name="files", required = false) MultipartFile[] files) {
	
		List<FileVO> flist = null;
		
		//fileHandler 생성	multipartFile -> flist
		if(files[0].getSize() > 0) {
			flist = fh.uploadFiles(files);
		}
		BoardDTO bdto = new BoardDTO(bvo, flist);
		
		log.info("boardModifyBdto >>> {}", bdto);
		
		int isOk = bsv.boardModify(bdto);
		
		log.info("boardModify >>> {}", isOk>0?"성공":"실패");
		
		return "redirect:/board/detail?bno="+bvo.getBno();
	}
	
	@DeleteMapping(value = "/remove/{uuid}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> remove(@PathVariable("uuid") String uuid){
		log.info("remove uuid >>> {}", uuid);
		
		int isOk = bsv.remove(uuid);
		log.info("remove >>> {}", isOk>0?"성공":"실패");
		
		return isOk>0? new ResponseEntity<String>("1", HttpStatus.OK)
				: new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
