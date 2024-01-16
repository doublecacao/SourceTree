package com.myWeb.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentVO {
	private long cno;
	private long bno;
	private String writer;
	private String content;
	private String regDate;
	private String modDate;
}
