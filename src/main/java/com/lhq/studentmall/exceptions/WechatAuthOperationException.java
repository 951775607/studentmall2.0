package com.lhq.studentmall.exceptions;

/**
 * @author Leon
 * @ClassName: ShopOperationException
 * @Description: 店铺异常类
 * @date 2019/3/3 16:33
 */
public class WechatAuthOperationException extends RuntimeException {


    private static final long serialVersionUID = -2517823685699194119L;

    public WechatAuthOperationException(String msg) {
        super(msg);
    }

}
