package com.lhq.studentmall.dao;//package com.lhq.student.dao;


import com.lhq.studentmall.StudentmallApplication;
import com.lhq.studentmall.entity.ShopCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Date 2019/3/2 23:09
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentmallApplication.class)
public class ShopCategoryDaoTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void queryShopCategoryById() {

        shopCategoryDao.queryShopCategoryById(33L);
    }


    @Test
    public void queryShopCategoryByIds() {
        List<Long> shopCategoryIdList = new ArrayList<>();
        shopCategoryIdList.add(33L);
        shopCategoryIdList.add(34L);
        shopCategoryIdList.add(35L);
        shopCategoryIdList.add(36L);
        shopCategoryDao.queryShopCategoryByIds(shopCategoryIdList);
    }

    @Test
    public void insertShopCategory() {
        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(38L);
        shopCategory.setParent(parent);
        shopCategory.setCreateTime(new Date());
        shopCategory.setLastEditTime(new Date());
        shopCategory.setPriority(10);
        shopCategory.setShopCategoryDesc("0401测试");
        shopCategory.setShopCategoryImg("fsdfsdfsdfsdfs");
        shopCategory.setShopCategoryName("0401名称987987");
        shopCategoryDao.insertShopCategory(shopCategory);
    }

    @Test
    public void updateShopCategory() {
        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(38L);
        shopCategory.setShopCategoryId(40L);
        shopCategory.setParent(parent);
        shopCategory.setCreateTime(new Date());
        shopCategory.setLastEditTime(new Date());
        shopCategory.setPriority(10);
        shopCategory.setShopCategoryDesc("0401测试222");
        shopCategory.setShopCategoryImg("fsdfsdfsdfsdfs");
        shopCategory.setShopCategoryName("0401名称222");
        shopCategoryDao.updateShopCategory(shopCategory);
    }


    @Test
    public void deleteShopCategory() {

        shopCategoryDao.deleteShopCategory(40L);
    }

    @Test
    public void batchDeleteShopCategory() {
        List<Long> shopCategoryIdList = new ArrayList<>();
        shopCategoryIdList.add(44L);
        shopCategoryIdList.add(41L);
        shopCategoryIdList.add(42L);
        shopCategoryIdList.add(43L);
        shopCategoryDao.batchDeleteShopCategory(shopCategoryIdList);

    }
}
