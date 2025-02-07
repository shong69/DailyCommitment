package com.example.demo.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;

/**
 * 스프링 컨테이너와 DB까지 연결한 통합 테스트
 * 테스트 전용 DB를 만들어서 사용하도록 한다.
 */
@SpringBootTest //스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional //transaction을 실행하고 DB에 데이터를 넣었다가 테스트가 끝나면 다시 지워줌
class MemberServiceIntegrationTest {
	//테스트 할 때는 autowired를 써서 편한 방법으로 쓰는거임
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	void 회원가입() {
		
		//Given
		Member member = new Member();
		member.setName("spring100");
		
		//When
		Long saveId = memberService.join(member);
		
		//THen
		Member findMember = memberRepository.findById(saveId).get();
		assertEquals(member.getName(), findMember.getName());
	}
	
	@Test
	void 중복_회원_예외() throws Exception {
		
		//Given
		Member member1 = new Member();
		member1.setName("spring");
		Member member2 = new Member();
		member2.setName("spring3000");
		
		//When
		memberService.join(member1);
		IllegalStateException e = assertThrows(IllegalStateException.class,
				() -> memberService.join(member2));
		
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		
	}
}
