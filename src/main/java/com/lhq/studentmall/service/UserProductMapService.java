package com.lhq.studentmall.service;

import com.lhq.studentmall.dto.UserProductMapExecution;
import com.lhq.studentmall.entity.UserProductMap;
import com.lhq.studentmall.exceptions.UserProductMapOperationException;

public interface UserProductMapService {
	/**
	 * 通过传入的查询条件分页列出用户消费信息列表
	 * 
	 * @param shopId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserProductMapExecution listUserProductMap(UserProductMap userProductCondition, Integer pageIndex,
											   Integer pageSize);

	/**
	 * 
	 * @param userProductMap
	 * @return
	 * @throws UserProductMapOperationException
	 */
	UserProductMapExecution addUserProductMap(UserProductMap userProductMap) throws UserProductMapOperationException;
}
