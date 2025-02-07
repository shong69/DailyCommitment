package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.Service.MemberService;
import com.example.demo.repository.MemberRepository;

@Configuration
public class SpringConfig {
	
//	private final DataSource ds;
//	private final EntityManager em;
	private final MemberRepository memberRepository; //spring data jpa가 구현해놓은 구현체를 injection 받음
	
	
//	@Autowired
//	public SpringConfig(DataSource ds, EntityManager em) {
//		this.ds = ds;
//		this.em = em;
//	}

	public SpringConfig(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Bean
	public MemberService memberService() {
		return new MemberService(memberRepository);
	}
//	
//	@Bean
//	public MemberRepository memberRepository() {
//		
//		return new JPAMemberRepository(em);
//	}
}
