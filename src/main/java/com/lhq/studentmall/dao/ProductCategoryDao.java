package com.lhq.studentmall.dao;


import com.lhq.studentmall.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Leon
 * @ClassName: ProductCategoryDao
 * @Description: 商品分类Dao
 * @date 2019/3/5 0:08
 */
@Mapper
public interface ProductCategoryDao {
    /**
     * 功能描述:通过ID查询店铺商品类别
     *
     * @param:
     * @return:
     **/
    List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 功能描述:批量新增商品类别
     *
     * @param:
     * @return:
     **/
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 功能描述:批量删除店铺商品分类
     *
     * @param productCategoryId
     * @param shopId
     * @return effectedNum
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);


}
