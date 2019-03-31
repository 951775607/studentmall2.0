package com.lhq.studentmall.service.impl;

import com.lhq.studentmall.dao.UserShopMapDao;
import com.lhq.studentmall.dto.UserShopMapExecution;
import com.lhq.studentmall.entity.UserShopMap;
import com.lhq.studentmall.service.UserShopMapService;
import com.lhq.studentmall.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserShopMapServiceImpl implements UserShopMapService {
	@Autowired
	private UserShopMapDao userShopMapDao;

	@Override
	public UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, int pageIndex, int pageSize) {
		// 空值判断
		if (userShopMapCondition != null && pageIndex != -1 && pageSize != -1) {
			// 页转行
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			// 根据传入的查询条件分页返回用户积分列表信息
			List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMapCondition, beginIndex,
					pageSize);
			// 返回总数
			int count = userShopMapDao.queryUserShopMapCount(userShopMapCondition);
			UserShopMapExecution ue = new UserShopMapExecution();
			ue.setUserShopMapList(userShopMapList);
			ue.setCount(count);
			return ue;
		} else {
			return null;
		}

	}

	/**
	 * 功能描述:根据传入的用户id查询该用户在某个店铺的积分信息
	 *
	 * @param:
	 * @return:
	 **/
	@Override
	public UserShopMap getUserShopMap(long userId, long shopId) {
		return userShopMapDao.queryUserShopMap(userId, shopId);
	}
}
