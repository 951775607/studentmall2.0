package com.lhq.studentmall.service.impl;


import com.lhq.studentmall.dao.ShopCategoryDao;
import com.lhq.studentmall.entity.ShopCategory;
import com.lhq.studentmall.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Leon
 * @ClassName: ShopCategoryServiceImpl
 * @Description: 店铺分类实现类
 * @date 2019/3/4 3:32
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    /**
     * 功能描述:查询店铺分类信息
     *
     * @param: shopCategoryCondition
     * @param: shopCategory
     * @return: queryShopCategory
     **/
    @Override
    public List<ShopCategory> getShopategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }

}
