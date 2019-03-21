package com.lhq.studentmall.service;


import com.lhq.studentmall.dto.WechatAuthExecution;
import com.lhq.studentmall.dto.WechatAuth;
import com.lhq.studentmall.exceptions.WechatAuthOperationException;

public interface WechatAuthService {

	/**
	 * 通过openId查找平台对应的微信账号
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);

	/**
	 * 注册本平台的微信账号
	 * @param wechatAuth
	 * @param profileImg
	 * @return
	 * @throws RuntimeException
	 */
	WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;

}
