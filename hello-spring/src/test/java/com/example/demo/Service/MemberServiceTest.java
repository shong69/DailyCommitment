package com.example.demo.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemoryMemberRepository;

class MemberServiceTest {

	MemberService memberService;
	MemoryMemberRepository memberRepository;
	
	@BeforeEach
	public void beforEach() {
		//테스트 실행 전마다 멤버서비스를 불러와서 외부에서 멤버리포지토리를 넣어주는 DI(Dependency Injection) 행위를 한다.
		memberRepository = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
		
	}
	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
	}
	
	@Test
	final void testJoin() {
		//given
		Member member = new Member();
		member.setName("hello");
		
		//when
		Long saveId = memberService.join(member);
		
		//then
		Member findMember= memberService.findOne(saveId).get();
		Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
	}
	
	@Test
	public void 중복_회원_예외() {
		//given
		Member member1 = new Member();
		member1.setName("spring");
		
		Member member2 = new Member();
		member2.setName("spring");
		
		//when
		memberService.join(member1);
		IllegalStateException e = assertThrows(IllegalStateException.class,() -> memberService.join(member2)); //2번째 인자를 실행했을 때 첫번째 인자의 오류가 터져야 성공
		
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//		memberService.join(member1);
//		try {
//			memberService.join(member2);
//			fail(); //예외가 발생해야 함
//		}catch (IllegalStateException e) {
//			//예외가 발생한 경우 예외메세지 확인해보기
//			//assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//		}
//		
		
		//then
		
	}

	@Test
	final void testFindMembers() {
		fail("Not yet implemented");
	}

	@Test
	final void testFindOne() {
		fail("Not yet implemented");
	}

}

