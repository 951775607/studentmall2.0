package com.lhq.studentmall.web.frontend;


import com.lhq.studentmall.dto.ProductExecution;
import com.lhq.studentmall.entity.Product;
import com.lhq.studentmall.entity.ProductCategory;
import com.lhq.studentmall.entity.Shop;
import com.lhq.studentmall.service.ProductCategoryService;
import com.lhq.studentmall.service.ProductService;
import com.lhq.studentmall.service.ShopService;
import com.lhq.studentmall.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:店铺详情页信息
 *
 * @param:
 * @return:
 **/
@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 功能描述:获取店铺信息以及店铺下面的商品类别列表
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(
            HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1) {
            //获取店铺信息
            shop = shopService.getByShopId(shopId);
            //获取店铺下面的商品类别列表
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 功能描述:查询条件分页列出该店铺下面的所有商品
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //获取店铺id
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        //空值判断
        if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
            //获取商品类别id
            long productCategoryId = HttpServletRequestUtil.getLong(request,
                    "productCategoryId");
            //获取模糊查找的商品名
            String productName = HttpServletRequestUtil.getString(request,
                    "productName");
            //组合查询条件
            Product productCondition = compactProductCondition4Search(shopId,
                    productCategoryId, productName);
            //按照查询条件以及分页信息返回相应的商品列表和总数
            ProductExecution pe = productService.getProductList(
                    productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "页大小、页码、商家id不能为空");
        }
        return modelMap;
    }

    /**
     * 功能描述:组合查询条件，将条件封装到productCondition对象返回
     *
     * @param:
     * @return:
     **/
    private Product compactProductCondition4Search(long shopId,
                                                   long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            //查询某个商品类别下面的商品列表
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            //查询名字里包含productName的店铺列表
            productCondition.setProductName(productName);
        }
        //只允许选出状态为上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}
