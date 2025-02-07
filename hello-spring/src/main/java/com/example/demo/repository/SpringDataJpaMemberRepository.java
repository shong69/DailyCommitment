package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Member;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository{
	
	@Override
	Optional<Member> findByName(String name); //spring data jpa가 구현체를 자동으로 공통 메서드들을 구현해준다. 
	
	//findByName(), findByEmail 처럼 메서드 이름만으로 조회 기능 제공
	//페이징 기능 자동 제공
	
	
}
