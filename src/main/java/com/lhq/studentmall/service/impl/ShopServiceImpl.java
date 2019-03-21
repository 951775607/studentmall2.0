package com.lhq.studentmall.service.impl;



import com.lhq.studentmall.dao.ShopAuthMapDao;
import com.lhq.studentmall.dao.ShopCategoryDao;
import com.lhq.studentmall.dao.ShopDao;
import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.dto.ShopExecution;
import com.lhq.studentmall.entity.Shop;
import com.lhq.studentmall.entity.ShopAuthMap;
import com.lhq.studentmall.enume.ShopStateEnum;
import com.lhq.studentmall.exceptions.ShopOperationException;
import com.lhq.studentmall.service.ShopService;
import com.lhq.studentmall.util.ImgeUtil;
import com.lhq.studentmall.util.PageCalculator;
import com.lhq.studentmall.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Leon
 * @ClassName: ShopServiceImpl
 * @Description: ShopService的实现类
 * @date 2019/3/3 15:51
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ShopAuthMapDao shopAuthMapDao;
    @Autowired
    private ShopCategoryDao shopCategoryDao;


    /**
     * 功能描述:通过shopid查询店铺
     *
     * @param: shopid
     * @return: Shop
     **/
    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }


    /**
     * 功能描述:更新店铺信息
     *
     * @param: shop
     * @param: shopImg
     * @return:
     **/
    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if (shop == null || shop.getShopId() == null) {
            System.out.println("modifyShop店铺或图片为空");
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            //1.判断是否需要处理图片,如果已经存在的话，则删除以前的照片重新上传
            try {
                if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    System.out.println("modifyShop需要删除的照片地址为：" + tempShop.getShopImg());
                    if (tempShop.getShopImg() != null) {
                        //System.out.println("确认删除！");
                        ImgeUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                     shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error:" + e.getMessage());
            }
        }
    }

    /**
     * 功能描述:分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域id，owner
     *
     * @param:shop
     * @param:rowindex 开始页
     * @param:pagesize 页大小
     * @return:list<shop></shop>
     **/
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        //转换行码
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        //依据查询条件，调用dao层返回相关的店铺列表
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        //依据相同的查询条件，返回店铺总数
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    /**
     * 功能描述:添加店铺
     *
     * @param:
     * @return:
     **/
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException{

        //判断店铺是否为空
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try {
            //店铺初始状态
            shop.setEnableStatus(0);
            shop.setAdvice("审核中");
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息1
            int effectedNum = shopDao.insertShop(shop);
            // throw new ShopOperationException("店铺创建失败");
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (thumbnail.getImage() != null) {
                    try {
                        //存储图片
                        addShopImg(shop, thumbnail);
                        //更新图片地址
                        effectedNum = shopDao.updateShop(shop);
                        if (effectedNum <= 0) {
                            throw new ShopOperationException("更新图片地址失败");
                        }


                        //执行增加shopAuthMao操作
                        ShopAuthMap shopAuthMap = new ShopAuthMap();
                        shopAuthMap.setEmployee(shop.getOwner());
                        shopAuthMap.setShop(shop);
                        shopAuthMap.setTitle("老板");
                        shopAuthMap.setTitleFlag(0);
                        shopAuthMap.setCreateTime(new Date());
                        shopAuthMap.setLastEditTime(new Date());
                        shopAuthMap.setEnableStatus(1);
                        try {
                            effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                            if (effectedNum <= 0) {
                                throw new ShopOperationException("授权创建失败");
                            }
                        } catch (Exception e) {
                            throw new ShopOperationException("insertShopAuthMap error:" + e.getMessage());
                        }

                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            throw new ShopOperationException("addShopImg error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }
//    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg){
//        //判断店铺是否为空
//        if (shop == null) {
//            return new ShopExecution(ShopStateEnum.NULL_SHOP);
//        }
//
//        try {
//            //店铺初始状态
//            shop.setEnableStatus(0);
//            shop.setCreateTime(new Date());
//            shop.setLastEditTime(new Date());
//            //添加店铺信息1
//            int effectedNum = shopDao.insertShop(shop);
//           // throw new ShopOperationException("店铺创建失败");
//            if (effectedNum <= 0) {
//                throw new ShopOperationException("店铺创建失败");
//            } else {
//                if (shopImg != null) {
//                    try {
//                        //存储图片
//                        addShopImg(shop, shopImg);
//                        //更新图片地址
//                        effectedNum = shopDao.updateShop(shop);
//                        if (effectedNum <= 0) {
//                            throw new ShopOperationException("更新图片地址失败");
//                        }
//                    } catch (Exception e) {
//                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            throw new ShopOperationException("addShopImg error:" + e.getMessage());
//        }
//        return new ShopExecution(ShopStateEnum.CHECK, shop);
//    }

    /**
     * 功能描述:添加图片
     *
     * @param:
     * @return:
     **/
    private void addShopImg(Shop shop, ImageHolder thumbnail) {

        //获取绝对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        System.out.println("addShopImg店铺相对路径dest:" + dest);
        String shopImgAddr = ImgeUtil.generateThumbnail(thumbnail, dest);
        System.out.println("addShopImg照片相对路径shopImgAddr:" + shopImgAddr);
        shop.setShopImg(shopImgAddr);
    }
//    private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
//        //获取绝对路径
//        String dest = PathUtil.getShopImagePath(shop.getShopId());
//        String shopImgAddr = ImgeUtil.generateThumbnail(shopImg, dest);
//        shop.setShopImg(shopImgAddr);
//    }



}
