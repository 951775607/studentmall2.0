package com.lhq.studentmall.web.frontend;


import com.lhq.studentmall.entity.Product;
import com.lhq.studentmall.service.ProductService;
import com.lhq.studentmall.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
			product = productService.getProductById(productId);
			modelMap.put("product", product);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品id为空！");
		}
		return modelMap;
	}
//
//	@RequestMapping(value = "/generateqrcode4product", method = RequestMethod.GET)
//	@ResponseBody
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
