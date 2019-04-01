package com.lhq.studentmall.web.frontend;


import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lhq.studentmall.entity.PersonInfo;
import com.lhq.studentmall.entity.Product;
import com.lhq.studentmall.service.ProductService;
import com.lhq.studentmall.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:获取商品详情页信息
 *
 * @param:
 * @return:
 **/
@Controller
@RequestMapping("/frontend")
public class ProductDetailController {
	@Autowired
	private ProductService productService;
//
//	private static String URLPREFIX = "https://open.weixin.qq.com/connect/oauth2/authorize?"
//			+ "appid=wxd7f6c5b8899fba83&"
//			+ "redirect_uri=115.28.159.6/myo2o/shop/adduserproductmap&"
//			+ "response_type=code&scope=snsapi_userinfo&state=";
//	private static String URLSUFFIX = "#wechat_redirect";

	/**
	 * 功能描述:根据商品id获取商品详情id
	 * @param:
	 * @return:
	 **/
	@RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductDetailPageInfo(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		Product product = null;
		if (productId != -1) {
			//根据productId获取商品信息id，包含商品详情图片列表
			product = productService.getProductById(productId);

			//项目2.0新增
			PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
			if (user == null) {
				modelMap.put("needQRCode", false);
			} else {
				modelMap.put("needQRCode", true);
			}
			modelMap.put("product", product);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品id为空！");
		}
		return modelMap;
	}


	// 微信获取用户信息的api前缀
	private static String urlPrefix;
	// 微信获取用户信息的api中间部分
	private static String urlMiddle;
	// 微信获取用户信息的api后缀
	private static String urlSuffix;
	// 微信回传给的响应添加顾客商品映射信息的url
	private static String productmapUrl;


	@Value("${wechat.prefix}")
	public void setUrlPrefix(String urlPrefix) {
		ProductDetailController.urlPrefix = urlPrefix;
	}

	@Value("${wechat.middle}")
	public void setUrlMiddle(String urlMiddle) {
		ProductDetailController.urlMiddle = urlMiddle;
	}

	@Value("${wechat.suffix}")
	public void setUrlSuffix(String urlSuffix) {
		ProductDetailController.urlSuffix = urlSuffix;
	}

	@Value("${wechat.productmap.url}")
	public void setProductmapUrl(String productmapUrl) {
		ProductDetailController.productmapUrl = productmapUrl;
	}



	/**
	 * 功能描述:生成消费凭证二维码，供操作员扫描，证明已消费，微信扫一扫就能连接
	 *
	 * @param:
	 * @return:
	 **/
	@RequestMapping(value = "/generateqrcode4product", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> generateQRCode4Product(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//获取商品id
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		//从session获取当前的顾客信息
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");
		if (productId != -1 && user != null && user.getUserId() != null) {
			//获取时间戳保证二维码的有效性
			long timpStamp = System.currentTimeMillis();
//			String content = "{\"productId\":" + productId + ",\"customerId\":"
//					+ user.getUserId() + ",\"createTime\":" + timpStamp + "}";
//			String longUrl = URLPREFIX + content + URLSUFFIX;
//			String shortUrl = ShortNetAddress.getShortURL(longUrl);
//			BitMatrix qRcodeImg = QRCodeUtil.generateQRCodeStream(shortUrl,
//					response);
			Long userId = user.getUserId();
			String content = "{aaaproductIdaaa:" + productId + ",aaacustomerIdaaa:" + user.getUserId()
					+ ",aaacreateTimeaaa:" + timpStamp + "}";

//			String content = "{\"productId\":" + productId + ",\"customerId\":"
//					+ user.getUserId();
			try {
				String longUrl = urlPrefix + productmapUrl + urlMiddle + content + urlSuffix;
				modelMap.put("success", true);
				modelMap.put("quior", longUrl);
//				MatrixToImageWriter.writeToStream(qRcodeImg, "png",
//						response.getOutputStream());
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		return modelMap;
	}
//	private void generateQRCode4Product(HttpServletRequest request,
//			HttpServletResponse response) {
//		long productId = HttpServletRequestUtil.getLong(request, "productId");
//		PersonInfo user = (PersonInfo) request.getSession()
//				.getAttribute("user");
//		if (productId != -1 && user != null && user.getUserId() != null) {
//			long timpStamp = System.currentTimeMillis();
//			String content = "{\"productId\":" + productId + ",\"customerId\":"
//					+ user.getUserId() + ",\"createTime\":" + timpStamp + "}";
//			String longUrl = URLPREFIX + content + URLSUFFIX;
//			String shortUrl = ShortNetAddress.getShortURL(longUrl);
//			BitMatrix qRcodeImg = QRCodeUtil.generateQRCodeStream(shortUrl,
//					response);
//			try {
//				MatrixToImageWriter.writeToStream(qRcodeImg, "png",
//						response.getOutputStream());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

}
