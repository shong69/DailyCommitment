package jpabook.jpashop.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.entity.item.Item;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;

@Transactional
@Service
public class OrderService {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ItemService itemService;
	
	/** 주문
	 * @param memberId
	 * @param itemId
	 * @param count
	 * @return orderId
	 */
	public Long order(Long memberId, Long itemId, int count) {
		
		//엔티티 조회
		Member member = memberRepository.fineOne(memberId);
		Item item = itemService.fineOne(itemId);
		
		//배송정보 생성
		Delivery delivery = new Delivery(member.getAddress());
		//주문상품 생성
		OrderItem orderItem = 
				OrderItem.createOrderItem(item, item.getPrice(), count);
		//주문 생성
		Order order = 
				Order.createOrder(member, delivery, orderItem);
		//주문 저장
		orderRepository.save(order);
		return order.getId();
	}
	
	/** 주문 취소
	 * @param orderId
	 */
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.fineOne(orderId);
		order.cancel();
	}
	
	/** 주문 검색
	 * @param orderSearch
	 * @return
	 */
	public List<Order> findOrders(OrderSearch orderSearch){
		return orderRepository.findAll(orderSearch);
	}
}
