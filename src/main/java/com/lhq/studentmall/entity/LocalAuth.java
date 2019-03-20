package com.lhq.studentmall.entity;

import java.util.Date;

/**
 * 
* @ClassName: LocalAuth 
* @Description: 本地账号实体类
* @author Leon
*
 */
public class LocalAuth {
	//本地账号ID
	private Long localAuthId;
	//用户名
	private String username;
	//密码
	private String password;
	//创建时间
	private Date createTime;
	//修改时间
	private Date lastEditTime;
	//关联用户实体类
	private PersonInfo personInfo;
	public Long getLocalAuthId() {
		return localAuthId;
	}
	public void setLocalAuthId(Long localAuthId) {
		this.localAuthId = localAuthId;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	
	
}
