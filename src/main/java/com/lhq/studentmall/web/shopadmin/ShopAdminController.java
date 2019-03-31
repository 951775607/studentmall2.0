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


    /**
     * 功能描述:转发至店铺授权页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/shopauthmanagement")
    public String shopAuthManagement() {
        return "shop/shopauthmanagement";
    }

    /**
     * 功能描述:转发至授权信息修改页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/shopauthedit")
    public String shopAuthEdit() {
        return "shop/shopauthedit";
    }


    /**
     * 功能描述:转发至操作失败的页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/operationsuccess", method = RequestMethod.GET)
    private String operationSuccess() {

        return "shop/operationsuccess";
    }

    /**
     * 功能描述:转发至操作成功的页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/operationfail", method = RequestMethod.GET)
    private String operationFail() {
        return "shop/operationfail";
    }



    @RequestMapping(value = "/productbuycheck", method = RequestMethod.GET)
    private String productBuyCheck() {
        // 转发至店铺的消费记录的页面
        return "shop/productbuycheck";
    }

    @RequestMapping(value = "/awardmanagement", method = RequestMethod.GET)
    private String awardManagement() {
        // 奖品管理页路由
        return "shop/awardmanagement";
    }

    /**
     * 功能描述:奖品编辑页面
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/awardoperation", method = RequestMethod.GET)
    private String awardEdit() {
        // 奖品编辑页路由
        return "shop/awardoperation";
    }

    @RequestMapping(value = "/usershopcheck", method = RequestMethod.GET)
    private String userShopCheck() {
        // 店铺用户积分统计路由
        return "shop/usershopcheck";
    }

    /**
     * 功能描述:
     * 
     * @param: 店铺用户积分兑换
     * @return: 
     **/
    @RequestMapping(value = "/awarddelivercheck", method = RequestMethod.GET)
    private String awardDeliverCheck() {
        // 店铺用户积分兑换路由
        return "shop/awarddelivercheck";
    }
}
