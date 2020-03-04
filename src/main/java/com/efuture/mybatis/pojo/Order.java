package com.efuture.mybatis.pojo;

import java.util.Date;
import java.util.List;

public class Order {
	private Integer id;
	private Long userId;
	private String orderNumber;
	private Date created;
	private Date updated;
	private User user;
	private List<OrderDetail> detailList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrderDetail> detailList) {
		this.detailList = detailList;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", orderNumber=" + orderNumber + ", created=" + created
				+ ", updated=" + updated + "]";
	}

}
