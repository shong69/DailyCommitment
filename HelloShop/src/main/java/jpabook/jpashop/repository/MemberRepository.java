package jpabook.jpashop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;

@Repository //Repository Bean으로 등록 
			//+ JPA 전용 예외(NoResultException)가 발생하면 스프링이 추상화한 예외(EmptyResultDataAccessException)로 변환해 서비스계층으로 반환
public class MemberRepository {
	//엔티티 매니저 팩토리를 주입받기 위해서는 @PersistenceUnit 어노테이션을 사용하면 된다.
	
	
	//스프링에서 EntityManger을 Bean으로 주입할 때 사용하는 어노테이션
	//컨테이너가 관리하는 엔티티 매니저를 주입해준다. -> 컨테이너가 제공하는 트랜잭션 기능과 연계할 수 있은ㅁ
	@PersistenceContext
	EntityManager em;
	
	
	/** 회원 엔티티 저장(영속화)
	 * @param member
	 */
	public void save(Member member) {
		em.persist(member);
	}
	
	/** 식별자로 회원 엔티티 조회
	 * @param id
	 * @return Member
	 */
	public Member fineOne(Long id) {
		return em.find(Member.class, id);
	}
	
	/** 회원 엔티티들을 조회
	 * @return List<Member>
	 */
	public List<Member> findAll(){
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}
	
	/** JPQL을 사용해 이름으로 회원 엔티티들을 조회
	 * @param name
	 * @return List<Member>
	 */
	public List<Member> findByName(String name){
		return em.createQuery("select m from Member m where m.name = :name",Member.class)
				.setParameter("name", name)
				.getResultList();
	}
	
}
