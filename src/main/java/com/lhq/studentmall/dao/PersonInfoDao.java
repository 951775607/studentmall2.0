package com.lhq.studentmall.dao;


import com.lhq.studentmall.entity.PersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PersonInfoDao {



	/**TODO
	 * 查询用户列表
	 * @param personInfoCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<PersonInfo> queryPersonInfoList(
            @Param("personInfoCondition") PersonInfo personInfoCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**TODO
	 *查询用户数量
	 * @param personInfoCondition
	 * @return
	 */
	int queryPersonInfoCount(
            @Param("personInfoCondition") PersonInfo personInfoCondition);

	/**
	 * 根据id查询用户
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);

	/**
	 * 添加用户
	 * @param wechatAuth
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);

	/**TODO
	 * 更新用户
	 * @param wechatAuth
	 * @return
	 */
	int updatePersonInfo(PersonInfo personInfo);

	/**TODO
	 * 删除用户
	 * @param wechatAuth
	 * @return
	 */
	int deletePersonInfo(long userId);
}
