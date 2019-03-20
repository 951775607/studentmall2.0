package com.lhq.studentmall.dao;





import com.lhq.studentmall.entity.ProductImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ProductImgDao {

	//查询商品图片列表
	List<ProductImg> queryProductImgList(long productId);

	//批量添加商品详情图片
	int batchInsertProductImg(List<ProductImg> productImgList);

	//删除指定商品下代所有详情图
	int deleteProductImgByProductId(long productId);
}
