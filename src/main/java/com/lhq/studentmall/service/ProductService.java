package com.lhq.studentmall.service;



import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.dto.ProductExecution;
import com.lhq.studentmall.entity.Product;
import com.lhq.studentmall.exceptions.ProductOperationException;

import java.util.List;

/**
 * @author Leon
 * @ClassName: ProductService
 * @Description: TODO
 * @date 2019/3/5 3:24
 */
public interface ProductService {

    /**
     * 功能描述:查询商品列表分页，可输入的条件有：商品名（模糊），商品状态，店铺ID，商品类别
     *
     * @param:
     * @return:
     **/
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 功能描述:通过商品id查询唯一的商品信息
     *
     * @param:
     * @return:
     **/
    Product getProductById(long productId);

    /**
     * 功能描述:添加商品以及图片处理
     *
     * @param: product
     * @param: thumbnail
     * @param: productImgs
     * @return:
     **/
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;

    /*
     * 功能描述:修改商品信息以及图片处理
     *
     * @param:
     * @return:
     **/
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException;


    /**
     * 删除商品
     *
     * @param
     * @return
     */
    void deleteProduct(Product product);

}
