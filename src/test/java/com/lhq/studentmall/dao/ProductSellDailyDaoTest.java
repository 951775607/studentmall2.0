package com.lhq.studentmall.dao;
import com.lhq.studentmall.entity.Product;
import com.lhq.studentmall.entity.ProductSellDaily;
import com.lhq.studentmall.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductSellDailyDaoTest {
	@Autowired
	private ProductSellDailyDao productSellDailyDao;

	/**
	 * 测试添加功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAInsertProductSellDaily() throws Exception {
//		ProductSellDaily productSellDaily = new ProductSellDaily();
//		Product product = new Product();
//		Shop shop = new Shop();
//		shop.setShopId(93L);
//		product.setShop(shop);
//
//		productSellDaily.setProduct(product);
//		productSellDaily.setShop(shop);
//		productSellDaily.setTotal(1);
//		productSellDaily.setCreateTime(new Date());

		// 创建商品日销量统计
		int effectedNum = productSellDailyDao.insertProductSellDaily();
//		assertEquals(3, effectedNum);
	}
	
	/**
	 * 测试添加功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBInsertDefaultProductSellDaily() throws Exception {
		// 创建商品日销量统计
		int effectedNum = productSellDailyDao.insertDefaultProductSellDaily();
//		assertEquals(10, effectedNum);
	}

	/**
	 * 测试查询功能
	 * 
	 * @throws Exception
	 */
	@Test
//	@Ignore
	public void testCQueryProductSellDaily() throws Exception {
		ProductSellDaily productSellDaily = new ProductSellDaily();
		// 叠加店铺去查询
		Shop shop = new Shop();
		shop.setShopId(93L);
		productSellDaily.setShop(shop);
		List<ProductSellDaily> productSellDailyList = productSellDailyDao.queryProductSellDailyList(productSellDaily,
				null, null);
		System.out.println(productSellDailyList.size());
	}
}
