package jpabook.jpashop.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@Service //Bean으로 등록
@Transactional //트랜잭션 적용(예외 발생 시 롤백)
public class MemberService {

	//스프링빈 주입(의존성 주입)
	@Autowired
	MemberRepository memberRepository;
	
	/** 회원가입
	 * @param member
	 * @return
	 */
	public Long join(Member member) {
		validateDuplicateMember(member); //중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = 
				memberRepository.findByName(member.getName());
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
	public Member findOne(Long memberId) {
		return memberRepository.fineOne(memberId);
	}
}
