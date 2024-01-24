package com.myWeb.www.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;
import com.myWeb.www.domain.FileVO;
import com.myWeb.www.domain.MemberDTO;
import com.myWeb.www.handler.ProfileHandler;
import com.myWeb.www.security.MemberVO;
import com.myWeb.www.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member/*")
@Controller
public class MemberController {
	@Inject
	private MemberService msv;
	
	@Inject
	private ProfileHandler ph;
	
	@Inject
	private BCryptPasswordEncoder bcEncoder;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(MemberVO mvo, RedirectAttributes re, @RequestParam(value = "files", required = false) MultipartFile file) {
		log.info("register mvo >>> {}", mvo);
		FileVO fvo = null;
		
		if(file.getSize() > 0) {
			fvo = ph.uploadProfile(file, mvo);
		}
		
		//기존 회원들의 id및 pwd정보를 가져와서 신규 회원과 중복되는지 비교
		List<MemberVO> mvoList = new ArrayList<MemberVO>(msv.mvoList());
		
		if(mvo.getPwd() == null || mvo.getPwd() == "") {
			re.addFlashAttribute("pwdCheck", "1");
			
			return "redirect:/member/register";
		}
		for(MemberVO email : mvoList) {
			if(mvo.getEmail().equals(email.getEmail())) {
				re.addFlashAttribute("emailCheck", "1");
				
				return "redirect:/member/register";
			}
		}
		
		mvo.setPwd(bcEncoder.encode(mvo.getPwd()));
		int isOk = msv.memberRegister(new MemberDTO(mvo, fvo));
		
		log.info("encPwd >>> {}", mvo.getPwd());
		
		log.info("memberRegister >>> {}", isOk>0?"성공":"실패");
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")
	public String login(HttpServletRequest request, RedirectAttributes re) {
		//로그인 실패시 다시 로그인 페이지로 돌아와 오류 메시지 전송
		//재로그인 유도
		re.addAttribute("email", request.getAttribute("email"));
		re.addAttribute("errorMsg", request.getAttribute("errorMsg"));
		
		return "redirect:/member/login";
	}
	
	//@RequestParam("email")String email : 쿼리스트링 (파라미터 받기)
	//Principal은 현재 로그인 중인 계정의 정보를 가져오고 mvo는 해당 페이지의 MemberVO를 가져오게된다
//	@GetMapping("/modify")
//	public void modify(Principal p, Model m) {
//		log.info("principal p >>> {}", p.toString());
//		
//		String email = p.getName();
//		
//		m.addAttribute("mvo", msv.detail(email));
//		m.addAttribute("mvoAuth", msv.selectAuth(email));
//	}
	
	@Transactional
	@GetMapping("/modify")
	public void modify(MemberVO mvo, Model m) {
		log.info("modifyMvo >>> {}", mvo);
		
		String email = mvo.getEmail();
		
		mvo = msv.detail(email);
		FileVO fvo = msv.getFile(email);
		
		log.info("mvo >>> {}", mvo);
		log.info("fvo >>> {}", fvo);
		
		MemberDTO mdto = new MemberDTO(mvo, fvo);
		
		m.addAttribute("mdto", mdto);
		m.addAttribute("mvoAuth", msv.selectAuth(email));
	}
	
	@PostMapping("/modify")
	public String modify(MemberVO mvo, HttpServletRequest request, HttpServletResponse response, Principal p, @RequestParam(name="files", required = false) MultipartFile file) {
		log.info("modify mvo >>> {}", mvo);
		
		FileVO profile = null;
		
		if(mvo.getPwd().equals(null) || mvo.getPwd().equals("")) {	//비밀번호가 null이거나 비어있을때
			if(file.getSize() > 0) {	//사진을 변경했을때
				
				ProfileHandler ph = new ProfileHandler();
				
				ph.fileRemover(msv.getFileDir(mvo.getEmail()));
				profile = ph.uploadProfile(file, mvo);
			}
			
			MemberVO Originmvo = msv.detail(mvo.getEmail());	//기존 비밀번호 db에서 가져오기
			mvo.setPwd(Originmvo.getPwd());	//기존 비밀번호 적용
			
			MemberDTO mdto = new MemberDTO(mvo, profile);
			int isOk = msv.modify(mdto);
			log.info("modify >>> {}", isOk>0?"성공":"실패");
			
			if(p.getName().equals(mvo.getEmail())) {	//로그인중인 계정의 이메일과 수정한 이메일이 같을 때
				Authentication authentication = SecurityContextHolder
						.getContext().getAuthentication();
				new SecurityContextLogoutHandler().logout(request, response, authentication);
			}
			return "redirect:/";
		}
		
		if(file.getSize() > 0) {
			ProfileHandler ph = new ProfileHandler();
			
			ph.fileRemover(msv.getFileDir(mvo.getEmail()));
			
			profile = ph.uploadProfile(file, mvo);
		}
		mvo.setPwd(bcEncoder.encode(mvo.getPwd()));	//신규 비밀번호 인코딩
		
		MemberDTO mdto = new MemberDTO(mvo, profile);
		
		int isOk = msv.modify(mdto);
		log.info("modify >>> {}", isOk>0?"성공":"실패");
		
		if(p.getName().equals(mvo.getEmail())) {	//로그아웃
			Authentication authentication = SecurityContextHolder
					.getContext().getAuthentication();
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/list")
	public void list(Model m) {
		List<MemberVO> mvoList = new ArrayList<MemberVO>(msv.mvoList());
		
		m.addAttribute("mvoList", mvoList);
	}
	
	@GetMapping("/delete")
	public String delete(MemberVO mvo, Principal p, HttpServletRequest request, HttpServletResponse response) {
		log.info("deleteMvo >>> {}", mvo);
		ProfileHandler ph = new ProfileHandler();
		
		int isOk = ph.fileRemover(msv.getFileDir(mvo.getEmail()));
		isOk = msv.delete(mvo.getEmail());
		
		if(p.getName().equals(mvo.getEmail())) {
			Authentication authentication = SecurityContextHolder
					.getContext().getAuthentication();
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		
		log.info("delete >>> {}", isOk>0?"성공":"실패");
		
		return "redirect:/member/list";
	}
	
}
