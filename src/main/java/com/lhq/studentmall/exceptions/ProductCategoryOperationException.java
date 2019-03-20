package com.lhq.studentmall.exceptions;

/**
 * @author Leon
 * @ClassName: ShopOperationException
 * @Description: 店铺异常类
 * @date 2019/3/3 16:33
 */
public class ProductCategoryOperationException extends RuntimeException {


    //生成序列化id
    private static final long serialVersionUID = -5561682028193762805L;

    public ProductCategoryOperationException(String msg) {
        super(msg);
    }

}
