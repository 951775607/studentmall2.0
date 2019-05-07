package com.lhq.studentmall.service;



import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.dto.ShopCategoryExecution;
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
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

//    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
    
    /**
     * 添加店铺类别，并存储店铺类别图片
     *
     * @param shopCategory
     * @param thumbnail
     * @return
     */
    ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder thumbnail);

    /**
     * 修改店铺类别
     *
     * @param shopCategory
     * @param thumbnail
     * @return
     */
    ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, ImageHolder thumbnail);

    /**
     * 根据Id返回店铺类别信息
     *
     * @param shopCategoryId
     * @return
     */
    ShopCategory getShopCategoryById(Long shopCategoryId);

    /**
     * 删除店铺类别
     *
     * @param area
     * @return
     */
    int delShopCategory(Integer shopCategoryId);
}
