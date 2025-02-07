package com.example.demo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.domain.Member;

public class MemoryMemberRepository implements MemberRepository{
	
	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L; //long 타입은 변수에 저장되기 전에 int 타입으로 임시저장되는데, 2147483647 이상은 오류 남. L로 선언해야 한다.
	
	@Override
	public Member save(Member member) {
		member.setId(++sequence);
		store.put(member.getId(), member);
		
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		return Optional.ofNullable(store.get(id));//객체를 Optional로 묶기 위해 of/ofNullable을 사용한다. ofNullable은 null값을 허용한다.
	}

	@Override
	public Optional<Member> findByName(String name) {
		return store.values().stream()
				.filter(member -> member.getName().equals(name))
				.findAny();
		
		//다른 표현 방법
		//for(Member member : store.values()){
		//	if(member.getName().equals(name)) {
		//		return Optional.of(member);
		//	}
		//}
		//Optioanl.empty();
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(store.values());
	}

	public void clearStore() {
		store.clear(); //Removes all of the mappings from this map 
	}
}
