package com.lhq.studentmall.service;



import com.lhq.studentmall.dto.ProductCategoryExecution;
import com.lhq.studentmall.entity.ProductCategory;
import com.lhq.studentmall.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * @author Leon
 * @ClassName: ProductCategoryService
 * @Description: 商品分类service
 * @date 2019/3/5 0:24
 */
public interface ProductCategoryService {
    /**
     * 功能描述:通过ID查询店铺商品类别
     *
     * @param:
     * @return:
     **/
    List<ProductCategory> getProductCategoryList(long shopId);


    /**
     * 功能描述:批量添加商品类别
     *
     * @param:
     * @return:
     **/
    ProductCategoryExecution batchAddProductCategory(
            List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    /**
     *功能描述:批量删除店铺商品类别，
     * 以后改良（暂时未实现）：先将此类别的商品里的商品类别id设置为空，再删除掉该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws RuntimeException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException;
}
