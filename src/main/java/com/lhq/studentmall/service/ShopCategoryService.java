package com.lhq.studentmall.service;



import com.lhq.studentmall.entity.ShopCategory;

import java.util.List;

/**
 * @author Leon
 * @ClassName: ShopCategoryService
 * @Description: 店铺分类
 * @date 2019/3/4 3:29
 */
public interface ShopCategoryService {
    /**
     * 功能描述:查询店铺分类信息
     *
     * @param: shopCategoryCondition
     * @param: shopCategory
     * @return: queryShopCategory
     **/
    List<ShopCategory> getShopategoryList(ShopCategory shopCategoryCondition);

}
