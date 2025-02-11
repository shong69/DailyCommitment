package com.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="ORDERS")
public class Order {
	@Id @GeneratedValue
	@Column(name="ORDER_ID")
	private Long id;
	
	//다대일관계
	@ManyToOne
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
	//일대다 관계
	@OneToMany(mappedBy="order")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

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
