package com.lhq.studentmall.service.impl;

import com.lhq.studentmall.dao.ProductCategoryDao;
import com.lhq.studentmall.dao.ProductDao;
import com.lhq.studentmall.dto.ProductCategoryExecution;
import com.lhq.studentmall.entity.ProductCategory;
import com.lhq.studentmall.enume.ProductCategoryStateEnum;
import com.lhq.studentmall.exceptions.ProductCategoryOperationException;
import com.lhq.studentmall.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Leon
 * @ClassName: ProductCategoryServiceImpl
 * @Description: TODO
 * @date 2019/3/5 0:25
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 功能描述:通过ID查询店铺商品类别
     *
     * @param:
     * @return:
     **/
    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }


    /**
     * 功能描述:批量添加商品类别
     *
     * @param:
     * @return:
     **/
    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectedNum = productCategoryDao
                        .batchInsertProductCategory(productCategoryList);
                if (effectedNum <= 0) {
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }

            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory error: " + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    /**
     *功能描述:批量删除店铺商品类别，
     * 以后改良（暂时未实现）：先将此类别的商品里的商品类别id设置为空，再删除掉该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws RuntimeException
     */
    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {

        //先将该商品类别下的所有商品分类Id设置为空
        try {
            int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectedNum < 0) {
                throw new RuntimeException("商品类别更新失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("商品类别删除失败： "
                    + e.getMessage());
        }
        //删除商品分类
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new RuntimeException("商品类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }

        } catch (Exception e) {
            throw new ProductCategoryOperationException("deleteProductCategory error: " + e.getMessage());
        }
    }
}
