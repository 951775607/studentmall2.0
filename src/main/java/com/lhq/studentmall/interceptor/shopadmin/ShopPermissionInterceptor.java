package com.lhq.studentmall.interceptor.shopadmin;



import com.lhq.studentmall.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 功能描述:店家管理系统操作验证拦截器
 *
 * @param:
 * @return:
 **/
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
    /**
     * 功能描述:拦截用户操作，继承HandlerInterceptor，实现preHandle方法
     *
     * @param:
     * @return:
     **/
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //从session中获取当前选择的店铺
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        //从session中获取当前用户可操作的店铺列表
        @SuppressWarnings("unchecked")
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute(
                "shopList");
        if (currentShop != null && shopList != null) {
            for (Shop shop : shopList) {
                if (shop.getShopId() == currentShop.getShopId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
