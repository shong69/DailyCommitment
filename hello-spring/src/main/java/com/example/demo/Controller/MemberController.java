package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Service.MemberService;
import com.example.demo.domain.Member;

@Controller
public class MemberController {
	private MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	
	/** 회원등록 화면 이동
	 * @return
	 */
	@GetMapping(value ="/members/new")
	public String createForm() {
		return "members/createMemberForm";
	}
	
	/** 회원등록
	 * @param form
	 * @return
	 */
	@PostMapping("/members/new")
	public String create(MemberForm form) {
		Member member = new Member();
		member.setName(form.getName());
		
		memberService.join(member);
		
		return "redirect:/"; // /로 돌아가도록 redirecting
	}
	
	@GetMapping("/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members",members);
		return "members/memberList";
		
	}
	
}
