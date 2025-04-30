package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jpabook.jpashop.domain.entity.item.Item;

@Entity
@Table(name="ORDER_ITEM")
public class OrderItem {
	@Id @GeneratedValue
	@Column(name="ORDER_ITEM_ID")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY) //지연 로딩 설정
	@JoinColumn(name="ITEM_ID")
	private Item item;
	
	@ManyToOne(fetch=FetchType.LAZY) //지연 로딩 설정
	@JoinColumn(name="ORDER_ID")
	private Order order;
	
	private int orderPrice;
	private int count;
	
	//==생성 메소드==//
	public static OrderItem createOrderItem(Item item, int orderPrice,int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		
		item.removeStock(count);// 재고 차감
		return orderItem;
	}
	//==비즈니스 로직==//
	/**
	 * 주문 취소(재고 반환)
	 */
	public void cancel() {
		getItem().addStock(count);
	}
	
	//==조회 로직==//
	/**
	 * 주문상품 전체 가격 조회
	 */
	public int getTotalPrice() {
		return getOrderPrice()*getCount();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public int getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
