package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Member extends BaseEntity{ //등록일과 수정일 상속받음

	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;
	
	private String name;
	//private String city;
	//private String street;
	//private String zipcode;
	@Embedded
	private Address address;

	//회원과 주문 일대다 관계
	@OneToMany(mappedBy="member")
	private List<Order> orders = new ArrayList<Order>();
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
}
