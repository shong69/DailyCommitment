package com.model.entity;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity { //기본 부모 엔티티 회원과 주문 클래스에 상속함

	private Date createdDate; //등록일
	private Date lastModifiedDate; //수정일
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
}
