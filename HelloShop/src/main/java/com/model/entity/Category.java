package com.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.model.entity.item.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Category {

	@Id @GeneratedValue
	@Column(name="CATEGORY_ID")
	private Long id;
	
	private String name;
	
	//다대다 양방향 주인)연결테이블을 바로 매핑함 -> 연결 엔티티 없이 매핑 완료됨
	@ManyToMany
	@JoinTable(name="CATEGORY_ITEM", //연결 테이블 지정
			joinColumns = @JoinColumn(name="CATEGORY_ID"), //매핑할 조인 컬럼 정보
			inverseJoinColumns = @JoinColumn(name="ITEM_ID")) //반대방향인 상품과 매핑할 조인 컬럼 정보
	private List<Item> items = new ArrayList<Item>();
	
	//카테고리의 계층구조를 위한 필드들
	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	private Category parent;
	
	@OneToMany(mappedBy="parent")
	private List<Category> child = new ArrayList<Category>();
	
	//==연관관계 메소드==//
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
	
	public void addItem(Item item) {
		items.add(item);
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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getChild() {
		return child;
	}

	public void setChild(List<Category> child) {
		this.child = child;
	}
}
