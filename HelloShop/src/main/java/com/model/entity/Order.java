package com.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="ORDERS")
public class Order extends BaseEntity{ //등록일과 수정일 상속받음
	@Id @GeneratedValue
	@Column(name="ORDER_ID")
	private Long id;
	
	//다대일관계
	@ManyToOne(fetch=FetchType.LAZY) //지연 로딩 설정
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
	//일대다 관계
	@OneToMany(mappedBy="order", //@OneToMany는 기본이 지연로딩
			cascade=CascadeType.ALL) //영속성 전이(주문-주문상품)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	//일대일 관계
	@OneToOne(cascade=CascadeType.ALL, //영속성 전이(주문-배송)
			fetch=FetchType.LAZY) //지연 로딩 설정
	@JoinColumn(name="DELIVERY_ID")
	private Delivery delivery;
	
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	


	//==연관관계 메소드==//
	public void setMember(Member member) {
		//기존 연관관계 제거
		if(this.member != null) {
			this.member.getOrders().remove(this);
		}
		
		this.member = member;
		member.getOrders().add(this); // 양방향 연관관계 설정
	}
	
	public void addOrderItem(OrderItem orderItem) {
		
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}
	
	//Getter, Setter
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Member getMember() {
		return member;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	
	
}
