package com.lhq.studentmall.dto;

import com.lhq.studentmall.entity.PersonInfo;

import java.util.Date;

/**
 * 
* @ClassName: WechatAuth 
* @Description: 微信账号实体类
* @author Leon
*
 */
public class WechatAuth {
	//用户id
	private Long wechatAuthId;
	//开放的接口id
	private String openId;
	//创建时间
	private Date createTime;
	//关联用户实体类
	private PersonInfo personInfo;
	
	
	public Long getWechatAuthId() {
		return wechatAuthId;
	}
	public void setWechatAuthId(Long wechatAuthId) {
		this.wechatAuthId = wechatAuthId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
}
