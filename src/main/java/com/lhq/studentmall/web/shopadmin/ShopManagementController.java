package com.lhq.studentmall.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.dto.ShopExecution;
import com.lhq.studentmall.entity.Area;
import com.lhq.studentmall.entity.PersonInfo;
import com.lhq.studentmall.entity.Shop;
import com.lhq.studentmall.entity.ShopCategory;
import com.lhq.studentmall.enume.ShopStateEnum;
import com.lhq.studentmall.service.AreaService;
import com.lhq.studentmall.service.ShopCategoryService;
import com.lhq.studentmall.service.ShopService;
import com.lhq.studentmall.util.CodeUtil;
import com.lhq.studentmall.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leon
 * @ClassName: ShopManagementController
 * @Description: 商家（店家）控制层
 * @date 2019/3/3 21:02
 */

@RequestMapping("/shopadmin")
@Controller
public class ShopManagementController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;


    /**
     * 功能描述:店铺操作认证
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String,Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        System.out.println("shopid:"+shopId);
        if (shopId <= 0) {
            //如果前端没有传回shopid，可以试着从request的session域中获取
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/student/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }


    /**
     * 功能描述:分页获取某个用户拥有店铺列表信息
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String,Object>();
//        PersonInfo user = new PersonInfo();
//        user.setUserId(9L);
//        user.setName("test");
//        request.getSession().setAttribute("user", user);
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        List<Shop> shopList = new ArrayList<>();
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            request.getSession().setAttribute("shopList", se.getShopList());
            modelMap.put("success", true);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("user", user);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }



    /**
     * 功能描述:根据店铺id获取店铺信息
     *
     * @param: shopId
     * @return: shop
     **/
    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody

    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("success", true);
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }


    /**
     * 功能描述:查询店铺分类
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 功能描述:修改店铺信息
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误！");
            return modelMap;
        }

        //1.接收并转化 相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        //1.1使用jackson把json格式转换为pojo类
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        MultipartHttpServletRequest multipartRequest = null;
        //1.2接收图片,srping自带的上传工具
        CommonsMultipartFile shopImg = null;
        //1.3文件上传解析器
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //1.4判断是否是流文件类型并转换
        if (commonsMultipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
        }
        //2、修改店铺
        if (shop != null && shop.getShopId() != null) {
//            PersonInfo owner = new PersonInfo();
//            owner.setUserId(1L);
//            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                if (shopImg == null) {
                    se = shopService.modifyShop(shop,  null);
                } else {
                    ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                    se = shopService.modifyShop(shop, imageHolder);

                }
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺Id");
            return modelMap;
        }
        //3、返回结果,已经定义在trycatch中了
    }


    /**
     * 功能描述:店铺注册
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误！");
            return modelMap;
        }

        //1.接收并转化 相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        //1.1使用jackson把json格式转换为pojo类
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        MultipartHttpServletRequest multipartRequest = null;
        //1.2接收图片,srping自带的上传工具
        CommonsMultipartFile shopImg = null;
        //1.3文件上传解析器
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //1.4判断是否是流文件类型并转换
        if (commonsMultipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空！");
            return modelMap;
        }

        //2、注册店铺
        if (shop != null && shopImg != null) {
//            PersonInfo owner = new PersonInfo();
//            owner.setUserId(1L);
//            shop.setOwner(owner);
            //添加用户信息
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                se = shopService.addShop(shop, imageHolder);
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    //该用户可以操作的店铺列表
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<>();
                    }
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息！");
            return modelMap;
        }
        //3、返回结果,已经定义在trycatch中了
    }


    /**
     * 功能描述:CommonsMultipartFilev转换为File类型
     *
     * @param:
     * @return:
     **/
//    private static void inputStreamToFile(InputStream ins, File file) {
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while ((bytesRead = ins.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("调用inputStreamToFile产生异常:" + e.getMessage());
//        }finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                if (ins != null) {
//                    ins.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("inputStreamToFile关闭IO产生异常:" + e.getMessage());
//            }
//        }
//    }

}