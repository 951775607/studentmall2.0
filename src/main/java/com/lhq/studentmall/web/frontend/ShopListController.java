package com.lhq.studentmall.web.frontend;

import com.lhq.studentmall.dto.ShopExecution;
import com.lhq.studentmall.entity.Area;
import com.lhq.studentmall.entity.Shop;
import com.lhq.studentmall.entity.ShopCategory;
import com.lhq.studentmall.service.AreaService;
import com.lhq.studentmall.service.ShopCategoryService;
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

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;

    /**
     * 功能描述:返回商品列表页里的ShopCategory列表（二级或者一级），以及区域信息列表
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //从前端获取parentId
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            //如果存在parentID,则取出一级ShopCategory下的二级ShopCategory列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            //如果parentId不存在，则取出所有一级ShopCategory（用户在首页选择的是全部商品列表）
            try {
                shopCategoryList = shopCategoryService.getShopategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            //获取区域列表信息
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    //获取指定查询条件下的店铺列表
    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取页大小
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if ((pageIndex > -1) && (pageSize > -1)) {
            //试着获取一级类别ID
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            //试着获取特定二级类别ID
            long shopCategoryId = HttpServletRequestUtil.getLong(request,
                    "shopCategoryId");
            //试着获取区域Id
//			long areaId = HttpServletRequestUtil.getLong(request, "areaId");
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            //试着获取模糊查询名字
            String shopName = HttpServletRequestUtil.getString(request,
                    "shopName");
            //获取组合之后的查询条件
            Shop shopCondition = compactShopCondition4Search(parentId,
                    shopCategoryId, areaId, shopName);
            //根据查询条件和分页信息获取店铺列表，并返回总数
            ShopExecution se = shopService.getShopList(shopCondition,
                    pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }

        return modelMap;
    }

    /**
     * 功能描述:组合查询条件，并将条件封装到ShopConditiond对象返回
     *
     * @param:
     * @return:
     **/
    private Shop compactShopCondition4Search(long parentId,
                                             long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            //查询某个一级ShopCategory下面的所有二级Sategory里面的店铺列表
            ShopCategory parentCategory = new ShopCategory();
            ShopCategory childCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L) {
            //查询某个二级ShopCategory下面的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L) {
            //查询位于某个区域ID下的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName != null) {
            //名字模糊查询
            shopCondition.setShopName(shopName);
        }
        //展示的是审核通过的店铺
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}


