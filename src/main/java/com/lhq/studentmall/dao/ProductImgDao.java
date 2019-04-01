package com.lhq.studentmall.dao;





import com.lhq.studentmall.entity.ProductImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ProductImgDao {

	/**
	 * 列出某个商品的详情图列表
	 *
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(long productId);

	/**
	 * 批量添加商品详情图片
	 *
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);

	/**
	 * 删除指定商品下的所有详情图
	 *
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
