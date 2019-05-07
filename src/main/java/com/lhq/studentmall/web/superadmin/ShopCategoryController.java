package com.lhq.studentmall.web.superadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhq.studentmall.dto.ConstantForSuperAdmin;
import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.dto.ShopCategoryExecution;
import com.lhq.studentmall.entity.ShopCategory;
import com.lhq.studentmall.enume.ShopCategoryStateEnum;
import com.lhq.studentmall.service.ShopCategoryService;
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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class ShopCategoryController {
	@Autowired
	private ShopCategoryService shopCategoryService;

	/**
	 * 获取所有店铺类别列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listshopcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> listShopCategorys() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> list = new ArrayList<ShopCategory>();
		try {
			// 获取所有一级店铺类别列表
			list = shopCategoryService.getShopCategoryList(null);
			// 获取所有二级店铺类别列表，并添加进以及店铺类别列表中
//			List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			list.addAll(shopCategoryService.getShopCategoryList(new ShopCategory()));
			modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, list);
			modelMap.put(ConstantForSuperAdmin.TOTAL, list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

	/**
	 * 删除店铺列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/removeshopcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeshopcategory(Integer shopCategoryId, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			int i = shopCategoryService.delShopCategory(shopCategoryId);
			if (i > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

	@RequestMapping(value = "/list1stlevelshopcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> list1stLevelShopCategorys() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> list = new ArrayList<ShopCategory>();
		try {
			// 获取所有一级店铺类别列表
			list = shopCategoryService.getShopCategoryList(null);
			modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, list);
			modelMap.put(ConstantForSuperAdmin.TOTAL, list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

	@RequestMapping(value = "/list2ndlevelshopcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> list2ndLevelShopCategorys() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> list = new ArrayList<ShopCategory>();
		try {
			// 获取所有二级店铺类别列表
			list = shopCategoryService.getShopCategoryList(new ShopCategory());
			modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, list);
			modelMap.put(ConstantForSuperAdmin.TOTAL, list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

	/**
	 * 添加店铺类别
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addshopcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addShopCategory(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		ShopCategory shopCategory = null;
		// 接收并转化相应的参数，包括店铺类别信息以及图片信息
		String shopCategoryStr = HttpServletRequestUtil.getString(request, "shopCategoryStr");
//		ImageHolder thumbnail = null;
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
//				request.getSession().getServletContext());
		try {
			shopCategory = mapper.readValue(shopCategoryStr, ShopCategory.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
//		// 咱们的请求中都带有multi字样，因此没法过滤，只是用来拦截外部非图片流的处理，
//		// 里边有缩略图的空值判断，请放心使用
//		try {
//			if (multipartResolver.isMultipart(request)) {
//				thumbnail = handleImage(request, thumbnail, "shopCategoryManagementAdd_shopCategoryImg");
//			}
//		} catch (Exception e) {
//			modelMap.put("success", false);
//			modelMap.put("errMsg", e.toString());
//			return modelMap;
//		}
		// 空值判断
//		if (shopCategory != null && thumbnail != null) {
		MultipartHttpServletRequest multipartRequest = null;
		//1.2接收图片,srping自带的上传工具
		CommonsMultipartFile shopImg = null;
		//1.3文件上传解析器
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//1.4判断是否是流文件类型并转换
		if (commonsMultipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopCategoryManagementAdd_shopCategoryImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空！");
			return modelMap;
		}
		if (shopCategory != null && shopImg != null) {
			try {
				// decode可能有中文的地方
				shopCategory.setShopCategoryName((shopCategory.getShopCategoryName() == null) ? null
						: (URLDecoder.decode(shopCategory.getShopCategoryName(), "UTF-8")));
				shopCategory.setShopCategoryDesc((shopCategory.getShopCategoryDesc() == null) ? null
						: (URLDecoder.decode(shopCategory.getShopCategoryDesc(), "UTF-8")));
				// 添加店铺类别信息
//				ShopCategoryExecution ae = shopCategoryService.addShopCategory(shopCategory, thumbnail);
				ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
				ShopCategoryExecution ae = shopCategoryService.addShopCategory(shopCategory, imageHolder);
				if (ae.getState() == ShopCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺类别信息");
		}
		return modelMap;
	}

	/**
	 * 修改店铺类别信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyshopcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShopCategory(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		ShopCategory shopCategory = null;
		// 接收并转化相应的参数，包括店铺类别信息以及图片信息
		String shopCategoryStr = HttpServletRequestUtil.getString(request, "shopCategoryStr");
//		ImageHolder thumbnail = null;
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
//				request.getSession().getServletContext());
		try {
			shopCategory = mapper.readValue(shopCategoryStr, ShopCategory.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// 咱们的请求中都带有multi字样，因此没法过滤，只是用来拦截外部非图片流的处理，
		// 里边有缩略图的空值判断，请放心使用
//		try {
//			if (multipartResolver.isMultipart(request)) {
//				thumbnail = handleImage(request, thumbnail, "shopCategoryManagementEdit_shopCategoryImg");
//			}
//		} catch (Exception e) {
//			modelMap.put("success", false);
//			modelMap.put("errMsg", e.toString());
//			return modelMap;
//		}
		MultipartHttpServletRequest multipartRequest = null;
		//1.2接收图片,srping自带的上传工具
		CommonsMultipartFile shopImg = null;
		//1.3文件上传解析器
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//1.4判断是否是流文件类型并转换
		if (commonsMultipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopCategoryManagementEdit_shopCategoryImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空！");
			return modelMap;
		}
		if (shopCategory != null && shopCategory.getShopCategoryId() != null) {
			try {
				// decode可能有中文的地方
				shopCategory.setShopCategoryName((shopCategory.getShopCategoryName() == null) ? null
						: (URLDecoder.decode(shopCategory.getShopCategoryName(), "UTF-8")));
				shopCategory.setShopCategoryDesc((shopCategory.getShopCategoryDesc() == null) ? null
						: (URLDecoder.decode(shopCategory.getShopCategoryDesc(), "UTF-8")));
				ShopCategoryExecution ae;
				if (shopImg != null) {
					ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
					 ae = shopCategoryService.modifyShopCategory(shopCategory, imageHolder);
				} else {
					 ae = shopCategoryService.modifyShopCategory(shopCategory, null);
				}
				if (ae.getState() == ShopCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺类别信息");
		}
		return modelMap;
	}

	/**
	 * 图片预处理
	 * 
	 * @param request
	 * @param thumbnail
	 * @param productImgList
	 * @return
	 * @throws IOException
	 */
	private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, String flowName)
			throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 取出缩略图并构建ImageHolder对象
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile(flowName);
		if (thumbnailFile != null) {
			thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
		}
		return thumbnail;
	}
}
