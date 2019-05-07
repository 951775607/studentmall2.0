package com.lhq.studentmall.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhq.studentmall.dao.ShopCategoryDao;
import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.dto.ShopCategoryExecution;
import com.lhq.studentmall.entity.Shop;
import com.lhq.studentmall.entity.ShopCategory;
import com.lhq.studentmall.enume.ShopCategoryStateEnum;
import com.lhq.studentmall.service.ShopCategoryService;
import com.lhq.studentmall.util.ImgeUtil;
import com.lhq.studentmall.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }

    @Override
    @Transactional
    public ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder thumbnail) {
        // 空值判断
        if (shopCategory != null) {
            // 设定默认值
            shopCategory.setCreateTime(new Date());
            shopCategory.setLastEditTime(new Date());
            if (thumbnail != null) {
                // 若上传有图片流，则进行存储操作，并给shopCategory实体类设置上相对路径
//                addThumbnail(shopCategory, thumbnail);
                //存储图片
                addShopImg(shopCategory, thumbnail);
                //更新图片地址
//                effectedNum = shopDao.updateShop(shop);
            }
            try {
                // 往数据库添加店铺类别信息
                int effectedNum = shopCategoryDao.insertShopCategory(shopCategory);
                if (effectedNum > 0) {
                    // 删除店铺类别之前在redis里存储的一切key,for简单实现
//                    deleteRedis4ShopCategory();
                    return new ShopCategoryExecution(ShopCategoryStateEnum.SUCCESS, shopCategory);
                } else {
                    return new ShopCategoryExecution(ShopCategoryStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException("添加店铺类别信息失败:" + e.toString());
            }
        } else {
            return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);
        }
    }

    @Override
    @Transactional
    public ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, ImageHolder thumbnail) {
        // 空值判断，主要判断shopCategoryId不为空
        if (shopCategory.getShopCategoryId() != null && shopCategory.getShopCategoryId() > 0) {
            // 设定默认值
            shopCategory.setLastEditTime(new Date());
            if (thumbnail != null) {
                // 若上传的图片不为空，则先获取之前的图片路径
                ShopCategory tempShopCategory = shopCategoryDao.queryShopCategoryById(shopCategory.getShopCategoryId());
                if (tempShopCategory.getShopCategoryImg() != null) {
                    // 若之前图片不为空，则先移除之前的图片
                    ImgeUtil.deleteFileOrPath(tempShopCategory.getShopCategoryImg());
                }
                // 存储新的图片
//                addThumbnail(shopCategory, thumbnail);
                addShopImg(shopCategory, thumbnail);
            }
            try {
                // 更新数据库信息
                int effectedNum = shopCategoryDao.updateShopCategory(shopCategory);
                if (effectedNum > 0) {
                    // 删除店铺类别之前在redis里存储的一切key,for简单实现
//                    deleteRedis4ShopCategory();
                    return new ShopCategoryExecution(ShopCategoryStateEnum.SUCCESS, shopCategory);
                } else {
                    return new ShopCategoryExecution(ShopCategoryStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException("更新店铺类别信息失败:" + e.toString());
            }
        } else {
            return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);
        }
    }

    /**
     * 存储图片
     *
     * @param shopCategory
     * @param thumbnail
     */
    private void addThumbnail(ShopCategory shopCategory, ImageHolder thumbnail) {
        String dest = PathUtil.getShopCategoryPath();
        String thumbnailAddr = ImgeUtil.generateNormalImg(thumbnail, dest);
        shopCategory.setShopCategoryImg(thumbnailAddr);
    }

//    /**
//     * 移除跟实体类相关的redis key-value
//     */
//    private void deleteRedis4ShopCategory() {
//        String prefix = SCLISTKEY;
//        // 获取跟店铺类别相关的redis key
//        Set<String> keySet = jedisKeys.keys(prefix + "*");
//        for (String key : keySet) {
//            // 逐条删除
//            jedisKeys.del(key);
//        }
//    }

    @Override
    public ShopCategory getShopCategoryById(Long shopCategoryId) {
        return shopCategoryDao.queryShopCategoryById(shopCategoryId);
    }

    /**
     * 功能描述:删除店铺类别
     *
     * @param:
     * @return:
     **/
    @Override
    public int delShopCategory(Integer shopCategoryId) {
        return shopCategoryDao.deleteShopCategory(shopCategoryId);
    }

    /**
     * 功能描述:添加图片
     *
     * @param:
     * @return:
     **/
    private void addShopImg(ShopCategory shopCategory, ImageHolder thumbnail) {

        //获取绝对路径
//        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String dest = PathUtil.getShopCategoryPath();
        System.out.println("相对路径dest:" + dest);
        String shopImgAddr = ImgeUtil.generateThumbnail(thumbnail, dest);
        System.out.println("照片相对路径shopImgAddr:" + shopImgAddr);
        shopCategory.setShopCategoryImg(shopImgAddr);
    }

}
