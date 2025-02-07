package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.Member;

import jakarta.persistence.EntityManager;

public class JPAMemberRepository implements MemberRepository {

	private final EntityManager em;
	
	public JPAMemberRepository(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Member save(Member member) {
		em.persist(member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		Member member = em.find(Member.class, id);
		
		return Optional.ofNullable(member);
	}

	@Override
	public Optional<Member> findByName(String name) {
		//JPQL createQuery <Member> TypedQuery<Member> jakarta.persistence.EntityManager.createQuery(String qlString, Class<Member> resultClass)
		List<Member> memberListFindByName = em.createQuery("select m from Member m where m.name = :name", Member.class)
				.setParameter("name", name) //setParameter(String name, Object value)
				.getResultList();
		return memberListFindByName.stream().findAny();
	}

	@Override
	public List<Member> findAll() {
		List<Member> memberList = em.createQuery("select * from Member m ", Member.class).getResultList();
		return memberList;
	}

}
