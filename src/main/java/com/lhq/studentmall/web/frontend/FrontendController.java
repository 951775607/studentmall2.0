package com.lhq.studentmall.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 功能描述:路由转发
 *
 * @param:
 * @return:
 **/
@Controller
@RequestMapping("/frontend")
public class FrontendController {
    @RequestMapping(value = "/mainpage", method = RequestMethod.GET)
    private String showMainPage() {
        return "frontend/mainpage";
    }

    /**
     * 功能描述:商品详情页路由
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/productdetail", method = RequestMethod.GET)
    private String showProductDetail() {
        return "frontend/productdetail";
    }

    /**
     * 功能描述:店铺详情页路由
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    private String showShopDetail() {
        return "frontend/shopdetail";
    }


    /**
     * 功能描述:商品列表页路由
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    private String showShopList() {
        return "frontend/shoplist";
    }

    /**
     * 功能描述:转发到前台首页
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index() {
        return "frontend/index";
    }

    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    private String index2() {
        return "frontend/index2";
    }


    @RequestMapping(value = "/mypoint", method = RequestMethod.GET)
    private String myPoint() {
        return "frontend/mypoint";
    }

    @RequestMapping(value = "/myrecord", method = RequestMethod.GET)
    private String myRecord() {
        return "frontend/myrecord";
    }

    @RequestMapping(value = "/pointrecord", method = RequestMethod.GET)
    private String pointRecord() {
        return "frontend/pointrecord";
    }

    @RequestMapping(value = "/awarddetail", method = RequestMethod.GET)
    private String awardDetail() {
        return "frontend/awarddetail";
    }

    @RequestMapping(value = "/customerbind", method = RequestMethod.GET)
    private String customerBind() {
        return "frontend/customerbind";
    }
}
