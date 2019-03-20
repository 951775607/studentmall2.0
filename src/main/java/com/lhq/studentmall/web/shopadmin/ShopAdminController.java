package com.lhq.studentmall.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Leon
 * @ClassName: ShopAdminController
 * @Description: 主要通过解析路由转发到相应的html中
 * @date 2019/3/4 1:32
 */
@Controller
@RequestMapping(value = "/shopadmin", method = {RequestMethod.GET})
public class ShopAdminController {
    /**
     * 功能描述:跳转到注册或修改页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";
    }

    /**
     * 功能描述:跳转到店铺列表页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }

    /**
     * 功能描述:店铺管理页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }


    /**
     * 功能描述:跳转到店铺商品类别页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/productcategorymanagement", method = RequestMethod.GET)
    public String productCategoryManage() {
        return "shop/productcategorymanagement";
    }


    /**
     * 功能描述:跳转到商品添加/编辑页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/productoperation", method = RequestMethod.GET)
    public String productOperation() {
        return "shop/productoperation";
    }

    /**
     * 功能描述:跳转到商品管理页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/productmanagement", method = RequestMethod.GET)
    public String productManagement() {
        return "shop/productmanagement";
    }

}
