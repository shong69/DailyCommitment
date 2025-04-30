package jpabook.jpashop.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;

@Repository
public class OrderRepository {
	@PersistenceContext
	EntityManager em;
	
	public void save(Order order) {
		em.persist(order);
	}
	
	public Order fineOne(Long id) {
		return em.find(Order.class, id);
	}
	
	/** Criteria를 사용해 주무내역을 조건에 따라 검색
	 * @param orderSearch
	 * @return
	 */
	public List<Order> findAll(OrderSearch orderSearch){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> cq = cb.createQuery(Order.class); //실제 검색 객체
		Root<Order> o = cq.from(Order.class); 
		
		List<Predicate> criteria = new ArrayList<Predicate>(); //조건 리스트
		
		//주문상태 검색
		if(orderSearch.getOrderStatus() != null) {
			Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
			criteria.add(status);
		}
		//회원 이름 검색
		if(StringUtils.hasText(orderSearch.getMemberName())) {
			//회원과 조인
			Join<Order, Member> m = o.join("member",JoinType.INNER);
			Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName()+"%");
									//타입 선언:member에서 name필드를 가져와 String으로 명세한다.
			criteria.add(name);
		}
		//검색 조건 합쳐서 and조건으로 나열
		cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		
		TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
		return query.getResultList();
	}
	
}
