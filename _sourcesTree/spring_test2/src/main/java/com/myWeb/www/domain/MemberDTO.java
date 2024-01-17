package com.myWeb.www.domain;

import com.myWeb.www.security.MemberVO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {
	private MemberVO mvo;
	private FileVO fvo;
}
