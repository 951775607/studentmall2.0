package com.lhq.studentmall.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.dto.ProductExecution;
import com.lhq.studentmall.entity.Product;
import com.lhq.studentmall.entity.ProductCategory;
import com.lhq.studentmall.entity.Shop;
import com.lhq.studentmall.enume.ProductStateEnum;
import com.lhq.studentmall.exceptions.ProductOperationException;
import com.lhq.studentmall.service.ProductCategoryService;
import com.lhq.studentmall.service.ProductService;
import com.lhq.studentmall.util.CodeUtil;
import com.lhq.studentmall.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
 * @ClassName: ProductManagementController
 * @Description: TODO
 * @date 2019/3/5 4:49
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    //支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    /**
     * 功能描述:根据商品id删除商品
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/deleteproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> deleteProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            //尝试获取前端传来的表单string流并将其转换成product实体类
            product = mapper.readValue(productStr, Product.class);
            if (product.getProductId() != null && currentShop.getShopId() != null) {
                product.setShop(currentShop);
                productService.deleteProduct(product);
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "删除商品失败！");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        modelMap.put("success", true);
        return modelMap;
    }


    /**
     * 功能描述:添加商品以及商品图片
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //接收前端参数的变量的初始化，包括商品、缩略图、详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        //CommonsMultipartFile thumbnail = null;
        //List<CommonsMultipartFile> productImgs = new ArrayList<CommonsMultipartFile>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //如果请求中存在文件流，在取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage((MultipartHttpServletRequest) request, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        try {
            //尝试从前端传过来的表单string流并转换成product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //如果product信息，缩略图以及详情图列表为非空，在开始进行商品添加操作
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                //从session中获取当前店铺的id并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
//                Shop shop = new Shop();
//                shop.setShopId(currentShop.getShopId());
                product.setShop(currentShop);
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    private ImageHolder handleImage(MultipartHttpServletRequest request, List<ImageHolder> productImgList) throws IOException {
        ImageHolder thumbnail;
        MultipartHttpServletRequest multipartRequest = request;
        //取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile =(CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());

        //取出详情图列表并构建List<ImageHolder>列表图像，最多支持刘张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            //取出第i个详情图片文件流不为空，在将其加入详情图列表
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                //取出第i个详情图片文件流不为空，在将其加入详情图列表
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                productImgList.add(productImg);
            } else {
                //取出第i个详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }


    /**
     * 功能描述:通过商品id获取商品信息
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //非空判断
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            //获取商品信息
            List<ProductCategory> productCategoryList = productCategoryService
                    .getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品Id为空！");
        }
        return modelMap;
    }

    /**
     * 功能描述:修改商品信息以及详情图片
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        //是商品编辑时调用还是上下架操作时候调用
        //如果是编辑则进行验证码判断，否则跳过验证码验证
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //验证码验证
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //接收前端参数的变量的初始化，包括商品，缩略图，详情列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        //如果请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage((MultipartHttpServletRequest) request, productImgList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            //尝试获取前端传来的表单string流并将其转换成product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null) {
            try {
                //从session中获取当前店铺的ID并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                //开始对商品信息进行变更操作
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }


    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取页码数
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取页大小
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //从当前session中获取当前的店铺信息，主要是为了获取shopid，减少对前端的依赖
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //空值判断
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            //获取传入的需要检索的条件，包括是否需要从某个商品类别以及模糊查询查找商品名去筛选店铺下的商品列表
            //筛选的条件可以进行排列组合
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            //传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "pageSize 或 pageIndex 或 shopId 为空！");
        }
        return modelMap;
    }
    private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        //如果有指定类别的要求则添加进去
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        //如果有商品名称模糊查询则添加进去
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }


}
