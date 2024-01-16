package com.myWeb.www.handler;

import java.util.List;

import com.myWeb.www.domain.CommentVO;
import com.myWeb.www.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PagingHandler {
	private int startPage;	//하단 페이지네이션의 시작 번호 1, 11, 21, ...
	private int endPage;	//하단 페이지네이션의 끝 번호 10, 20, 30, ...
	private boolean prev, next;
	
	private int totalCount;	//총게시글 수 (매개변수로 전달)
	private PagingVO pgvo;	//매개변수로 전달
	
	private List<CommentVO> cmtList;
	
	//생성자에서 모든 값들이 설정되어야 함
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		this.endPage = (int)Math.ceil(pgvo.getPageNo() / (double)pgvo.getQty())*pgvo.getQty();
		
		this.startPage = endPage-9;
		
		//실제 마지막 페이지
		//전체 글 수/한페이지에 표시되는 게시글 수 => 올림
		int realEndPage = (int)Math.ceil(totalCount/(double)pgvo.getQty());
		
		if(realEndPage < endPage) {
			this.endPage = realEndPage;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEndPage;
	}
	
	public PagingHandler(PagingVO pgvo, int totalCount, List<CommentVO> cmtList) {
		this(pgvo, totalCount);
		this.cmtList = cmtList;
	}
}
