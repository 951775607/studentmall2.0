package com.lhq.studentmall.dao;


import com.lhq.studentmall.entity.ShopCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Leon
 * @ClassName: ShopCategoryDao
 * @Description: 店铺分类dao
 * @date 2019/3/4 2:51
 */
@Mapper
public interface ShopCategoryDao {
    /**
     * 功能描述:查询店铺分类信息
     *
     * @param: shopCategoryCondition
     * @param: shopCategory
     * @return: queryShopCategory
     **/
//    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategory);
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);

}
