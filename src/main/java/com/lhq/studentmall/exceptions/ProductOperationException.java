package com.lhq.studentmall.exceptions;

/**
 * @author Leon
 * @ClassName: ShopOperationException
 * @Description: 店铺异常类
 * @date 2019/3/3 16:33
 */
public class ProductOperationException extends RuntimeException {

    //生成序列化id
    private static final long serialVersionUID = -4302620039083774925L;
    public ProductOperationException(String msg) {
        super(msg);
    }

}
