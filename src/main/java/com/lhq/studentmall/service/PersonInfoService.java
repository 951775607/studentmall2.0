package com.lhq.studentmall.service;


import com.lhq.studentmall.dto.PersonInfoExecution;
import com.lhq.studentmall.entity.PersonInfo;

public interface PersonInfoService {

	/**
	 * 描述：根据用户id获取personinfo信息
	 * @param userId
	 * @return
	 */
	PersonInfo getPersonInfoById(Long userId);

	/**
	 * 
	 * @param personInfoCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition,
										  int pageIndex, int pageSize);

	/**
	 * 
	 * @param personInfo
	 * @return
	 */
	PersonInfoExecution addPersonInfo(PersonInfo personInfo);

	/**
	 * 
	 * @param personInfo
	 * @return
	 */
	PersonInfoExecution modifyPersonInfo(PersonInfo personInfo);

}
