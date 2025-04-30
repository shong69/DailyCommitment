package jpabook.jpashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.Service.OrderService;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.entity.item.Book;
import jpabook.jpashop.domain.entity.item.Item;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

	@PersistenceContext
	EntityManager em;
	
	@Autowired OrderService orderService;
	@Autowired OrderRepository orderRepository;
	
	@Test
	public void 상품주문() throws Exception{
	
		//Given
		Member member = createMember();
		Item item = createBook("시골JPA",10000,10); //이름, 가격, 재고
		int orderCount = 2;
		
		//When
		Long orderId = orderService.order(member.getId(),item.getId(),orderCount);

		//Then
		Order getOrder =orderRepository.fineOne(orderId);
		
		assertEquals("상품 주문 시 상태는 ORDER",OrderStatus.ORDER,getOrder.getStatus());
		assertEquals("주문한 상품 종류 수가 정확해야 한다.",1,getOrder.getOrderItems().size());
		assertEquals("주문 가격은 가격*수량이다.",10000*2, getOrder.getTotalPrice());
		assertEquals("주문 수향만큼 재고가 줄어야 한다.",8,item.getStockQuantity());

	}
	
	@Test
	public void 상품주문_재고수량초과(){
		//Given
		Member member = createMember();
		Item item = createBook("시골 JPA",10000,10); //이름, 가격 재고
		int orderCount = 11;
		
		//When, Then
	    assertThrows(NotEnoughStockException.class, () -> {
	        orderService.order(member.getId(), item.getId(), orderCount); //11개 주문
	    });
	}
	
	@Test
	public void 주문취소() {
		//Given
		Member member = createMember();
		Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
		int orderCount =2 ;
		
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
		
		//When
		orderService.cancelOrder(orderId);
		
		//Then
		Order getOrder = orderRepository.fineOne(orderId);
		
		assertEquals("주문 취소 시 상태는 CANCEL이다",OrderStatus.CANCEL,getOrder.getStatus());
		assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.",10,item.getStockQuantity());
	}

	private Item createBook(String string, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(string);
		book.setStockQuantity(stockQuantity);
		book.setPrice(price);
		em.persist(book);
		return book;
	}

	private Member createMember() {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울","강가","123-123"));
		em.persist(member);
		return member;
	}
}
