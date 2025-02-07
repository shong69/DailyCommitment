package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;



@Transactional(rollbackFor = Exception.class)
public class MemberService {
	//private final MemberRepository memberRepository = new MemoryMemberRepository(); 
	//인터페이스를 구현한 클래스로 객체 생성하여 사용하기 -> 추상화, 구현체 교체 용이, 의존성 주입
	private final MemberRepository memberRepository;
	

	public MemberService(MemberRepository memberRepository) {
//		//MemberRepository를 외부에서 생성하면서 넣어주도록 한다 -> test에서 repository를 사용할 때마다 service에 repository를 넣어서 재사용하도록 함
//		<test 코드>
//		MemberService memberService;
//		MemoryMemberRepository memberRepository;
//		
//		@BeforeEach
//		public void beforEach() {
//			//테스트 실행 전마다 멤버서비스를 불러와서 외부에서 멤버리포지토리를 넣어주는 DI(Dependency Injection) 행위를 한다.
//			memberRepository = new MemoryMemberRepository();
//			memberService = new MemberService(memberRepository);
//			
//		}
		this.memberRepository = memberRepository;
	}


	/**
	 * 회원가입
	 * @param member
	 * @return id
	 */
	public Long join(Member member) {
		validateDuplicateMember(member); //중복 회원 검증
		
		memberRepository.save(member);
		return member.getId();
	}


	private void validateDuplicateMember(Member member) {
		//Optional의 ifPresent 사용
		memberRepository.findByName(member.getName())
		.ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
		
	}
	
	/** 전체 회원 조회
	 * @return
	 */
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
	public Optional<Member> findOne(Long memberId){
		return memberRepository.findById(memberId);
	}

}
