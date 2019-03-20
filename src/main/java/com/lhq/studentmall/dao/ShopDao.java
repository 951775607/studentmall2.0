package com.lhq.studentmall.dao;

import com.lhq.studentmall.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Leon
 * @ClassName: ShopDao
 * @Description: 店铺的持久层
 * @date 2019/3/3 1:42
 */
@Mapper
public interface ShopDao {

    /**
     * 功能描述:分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域id，owner
     *
     * @param:shop
     * @param:rowindex 开始页
     * @param:pagesize 页大小
     * @return:list<shop></shop>
     **/
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 功能描述:获取总记录数
     *
     * @param:
     * @return:
     **/
    int queryShopCount(@Param("shopCondition") Shop shopCondition);


    /**
     * 功能描述:通过shopid查询店铺
     *
     * @param: shopid
     * @return: Shop
     **/
    Shop queryByShopId(long shopId);

    /**
     * 功能描述:新增店铺
     *
     * @param: shop
     * @return: 0失败    1成功
     **/
    int insertShop(Shop shop);

    /**
     * 功能描述:更新店铺信息
     *
     * @param: shop
     * @return: 0失败    1成功
     **/
    int updateShop(Shop shop);
}
