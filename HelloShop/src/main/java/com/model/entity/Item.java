package com.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) //상속 관계 매핑
@DiscriminatorColumn(name="DTYPE") //DTYPE 으로 구분
public abstract class Item {

	@Id @GeneratedValue
	@Column(name="ITEM_ID")
	private Long id;
	
	private String name; //이름
	private int price;   //가격
	private int stockQuantity; //재고수량
	
	//다대다 양방향
	@ManyToMany(mappedBy="items")
	private List<Category> categories = new ArrayList<Category>(); 
	
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	
}
