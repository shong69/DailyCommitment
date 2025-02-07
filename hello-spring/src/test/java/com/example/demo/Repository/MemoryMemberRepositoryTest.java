package com.example.demo.Repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemoryMemberRepository;

class MemoryMemberRepositoryTest { //외부노출 필요 없으니까 public X default 접근제어자를 사용한다.
	MemoryMemberRepository repository = new MemoryMemberRepository();
	
	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}
	
	@Test
	public void save() {
		//given -> BDD(Behavior-Drive Development) // 초기 상태 설정
		Member member = new Member();
		
		member.setName("spring");
		
		//when 행동 수행
		repository.save(member);
		
		//then 결과 검증
		Member result = repository.findById(member.getId()).get();
		assertThat(result).isEqualTo(member);
	}
	
	@Test
	public void findById() {
    	//given
    	Member member = new Member(); //멤버 객체
        member.setName("shong"); //회원 이름 설정 메소드
    	
        //when
    	repository.save(member); //레포지토리에 저장 메소드
        
        //then
        Member result = repository.findById(member.getId()).get(); 
        //잘 저장되었는지 확인(해당 아이디를 저장할 때 Optional.ofNullable()로 값을 반환하기 때문에 
        //get메소드로 꺼낼 수 있다)
        
        
    	Assertions.assertEquals(result, member); 
        //확인방법 1. 저장한 멤버와 꺼낸 값이 같은지 확인
        //Assertions.assertThat(member).isEqualTo(result);
        //확인방법 2. assertJ의 라이브러리로, 타겟인 멤버와 결과가 같은지 확인
	}
	
	@Test
	public void findByName() {
		
		//given
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		//when
		Member result = repository.findByName("spring1").get(); //get : If a value is present, returns the value, otherwise throws NoSuchElementException. 
																//Returns:• the non-null value described by this Optional
		//then
		assertThat(result).isEqualTo(member1); //assertJ의 라이브러리. 타겟인 멤버와 같은지 확인해준다
		
	}
	
	@Test
	public void findAll() {
		//given
		Member member1 = new Member();
		member1.setName("srping1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		//when
		List<Member> result = repository.findAll();
		
		//then
		assertThat(result.size()).isEqualTo(2);
	}
	
	
}
