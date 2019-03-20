package com.lhq.studentmall.entity;

import java.util.Date;

/**
 * 功能描述:顾客已经领取的奖品列表
 *
 * @param:
 * @return:
 **/
public class UserAwardMap {

	//主键
	private Long userAwardId;
	//创建时间
	private Date createTime;
	//使用状态码，0是未使用，1是已经使用
	private Integer usedStatus;
	//领取奖品消耗的积分
	private Integer point;
	//顾客实体类
	private PersonInfo user;
	//奖品信息实体类
	private Award award;
	//店铺信息实体类
	private Shop shop;
	//店铺员工信息实体类
	private PersonInfo operator;

	public UserAwardMap() {
	}
	public UserAwardMap(Long userAwardId, Date createTime, Integer usedStatus, Integer point, PersonInfo user, Award award, Shop shop, PersonInfo operator) {
		this.userAwardId = userAwardId;
		this.createTime = createTime;
		this.usedStatus = usedStatus;
		this.point = point;
		this.user = user;
		this.award = award;
		this.shop = shop;
		this.operator = operator;
	}

	public Long getUserAwardId() {
		return userAwardId;
	}

	public void setUserAwardId(Long userAwardId) {
		this.userAwardId = userAwardId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUsedStatus() {
		return usedStatus;
	}

	public void setUsedStatus(Integer usedStatus) {
		this.usedStatus = usedStatus;
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

	public Award getAward() {
		return award;
	}

	public void setAward(Award award) {
		this.award = award;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public PersonInfo getOperator() {
		return operator;
	}

	public void setOperator(PersonInfo operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "UserAwardMap{" +
				"userAwardId=" + userAwardId +
				", createTime=" + createTime +
				", usedStatus=" + usedStatus +
				", point=" + point +
				", user=" + user +
				", award=" + award +
				", shop=" + shop +
				", operator=" + operator +
				'}';
	}
}
