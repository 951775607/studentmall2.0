package com.lhq.studentmall.dao;


import com.lhq.studentmall.entity.WechatAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WechatAuthDao {
	/**
	 * 通过openId查询对应的本平台的微信账号
	 * @param openId
	 * @return
	 */
	WechatAuth queryWechatInfoByOpenId(String openId);

	/**
	 * 添加本平台微信账号
	 * @param wechatAuth
	 * @return
	 */
	int insertWechatAuth(WechatAuth wechatAuth);

	/**
	 * 删除平台微信账号
	 * @param wechatAuthId
	 * @return
	 */
	int deleteWechatAuth(Long wechatAuthId);
}
