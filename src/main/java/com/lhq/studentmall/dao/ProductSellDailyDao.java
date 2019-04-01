package com.lhq.studentmall.dao;
import com.lhq.studentmall.entity.ProductSellDaily;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProductSellDailyDao {
	/**
	 * 根据查询条件返回商品日销售的统计列表
	 * 
	 * @param userProductCondition
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<ProductSellDaily> queryProductSellDailyList(
            @Param("productSellDailyCondition") ProductSellDaily productSellDailyCondition,
            @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

	/**
	 * 统计平台所有商品的日销售量
	 * 
	 * @return
	 */
	int insertProductSellDaily();

	/**
	 * 统计平台当天没销量的商品，补全信息，将他们的销量置为0
	 * 
	 * @return
	 */
	int insertDefaultProductSellDaily();


	/**
	 * 功能描述:删除商品销量，给删除商品的时候时候，不然外键约束无法删除
	 *
	 * @param:
	 * @return:
	 **/
	int deleteProductByProductId(@Param("productId") long productId,
								 @Param("shopId") long shopId);
}
