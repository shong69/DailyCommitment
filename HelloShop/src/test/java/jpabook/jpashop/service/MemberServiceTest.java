package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.Service.MemberService;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

//스프링 컨테이너에서 JUnit 테스트가 진행되도록함 -> Autowired 사용 가능
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//테스트에서 사용하면 실행할 때마다 트랜잭션을 시작하고, 끝나면 강제로 롤백하게 된다.
@Transactional
public class MemberServiceTest {
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	public void 회원가입() throws Exception{
		
		//Given : 회원 엔티티 생성
		Member member = new Member();
		member.setName("kim");
		
		 //When : 회원가입 시도
		Long saveId = memberService.join(member);
		
		//Then : 저장한 회원과 같은지 검증
		assertEquals(member, memberRepository.fineOne(saveId)); //두 매개변수가 같은지
	}
	//지정한 예외 클래스가 발생해야 테스트 성공
	@Test
	public void 중복_회원_예외() {
		
		//Given
		Member member1 = new Member();
		member1.setName("kim");
		
		Member member2 = new Member();
		member2.setName("kim");
		
		//When
		IllegalStateException exception = assertThrows(IllegalStateException.class, ()->{			
			memberService.join(member1);
			memberService.join(member2); //예외 발생 
		});
		
		
		//Then
		//IllegalStateException의 message를 아래처럼 설정해야 쓸 수 있는 코드
//		assertEquals("예외가 발생해야 한다.",exception.getMessage()); //-> 실행되지 않는 코드가 된다.
		//만약 fail이 호출되거나 IllegalException이 발생하지 않으면 테스트는 실해
	}
	
	
}
