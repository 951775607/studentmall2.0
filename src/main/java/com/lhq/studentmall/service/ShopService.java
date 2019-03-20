package com.lhq.studentmall.service;


import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.dto.ShopExecution;
import com.lhq.studentmall.entity.Shop;
import com.lhq.studentmall.exceptions.ShopOperationException;

/**
 * @author Leon
 * @ClassName: ShopService
 * @Description: TODO
 * @date 2019/3/3 17:24
 */

public interface ShopService {

    /**
     * 功能描述:分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域id，owner
     *
     * @param:shop
     * @param:rowindex 开始页
     * @param:pagesize 页大小
     * @return:list<shop></shop>
     **/
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

    /**
     * 功能描述:创建店铺
     *
     * @param:
     * @return:
     **/
    //ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) throws ShopOperationException;
    // ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 功能描述:通过shopid查询店铺
     *
     * @param: shopid
     * @return: Shop
     **/
    Shop getByShopId(long shopId);

    /**
     * 功能描述:更新店铺信息
     *
     * @param: shop
     * @param: shopImg
     * @return:
     **/
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}
