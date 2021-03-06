package com.lhq.studentmall.entity;

import java.util.Date;

/**
 * 功能描述:店铺顾客积分
 *
 * @param:
 * @return:
 **/
public class UserShopMap {
	//主键id
	private Long userShopId;
	//创建时间
	private Date createTime;
	//顾客积分
	private Integer point;
	//顾客实体类
	private PersonInfo user;
	//店铺信息实体类
	private Shop shop;

	public Long getUserShopId() {
		return userShopId;
	}

	public void setUserShopId(Long userShopId) {
		this.userShopId = userShopId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public PersonInfo getUser() {
		return user;
	}

	public void setUser(PersonInfo user) {
		this.user = user;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	public String toString() {
		return "UserShopMap{" +
				"userShopId=" + userShopId +
				", createTime=" + createTime +
				", point=" + point +
				", user=" + user +
				", shop=" + shop +
				'}';
	}
}
