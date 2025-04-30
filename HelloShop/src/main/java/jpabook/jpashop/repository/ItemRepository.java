package jpabook.jpashop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.entity.item.Item;

@Repository
public class ItemRepository {
	@PersistenceContext
	EntityManager em;
	
	/** 아이템 엔티티 저장(persist) 혹은 수정(merge)
	 * @param item
	 */
	public void save(Item item) {
		//id가 자동생성해야 정상동작한다. (@GeneratedValue 사용)
		if(item.getId() == null) {
			em.persist(item);
		}else {
			em.merge(item); //상품 수정 시 병합하게 됨
		}
	}
	
	/** 식별자로 아이템 찾기
	 * @param id
	 * @return
	 */
	public Item fineOne(Long id) {
		return em.find(Item.class, id);
	}
	
	/** 모든 아이템 리스트로 반환
	 * @return
	 */
	public List<Item> findAll(){
		return em.createQuery("select i from Item i", Item.class).getResultList();
	}
}
