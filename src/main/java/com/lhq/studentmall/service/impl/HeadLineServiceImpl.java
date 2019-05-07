package com.lhq.studentmall.service.impl;


import com.lhq.studentmall.dao.HeadLineDao;
import com.lhq.studentmall.dto.HeadLineExecution;
import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.entity.HeadLine;
import com.lhq.studentmall.entity.ShopCategory;
import com.lhq.studentmall.enume.HeadLineStateEnum;
import com.lhq.studentmall.service.HeadLineService;
import com.lhq.studentmall.util.ImgeUtil;
import com.lhq.studentmall.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class HeadLineServiceImpl implements HeadLineService {
	@Autowired
	private HeadLineDao headLineDao;
	private static String HLLISTKEY = "headlinelist";


	/**
	 *
	 * 根据传入的条件返回指定的头条列表
	 * @param headLineCondition
	 * @return
	 * @throws IOException
	 */
	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition)
			throws IOException {
		return headLineDao.queryHeadLine(headLineCondition);
	}

	@Override
	@Transactional
	public HeadLineExecution addHeadLine(HeadLine headLine, ImageHolder thumbnail) {
		// 空值判断
		if (headLine != null) {
			// 设定默认值
			headLine.setCreateTime(new Date());
			headLine.setLastEditTime(new Date());
			// 若传入的头条图片为非空，则存储图片并在实体类里将图片的相对路径设置上
			if (thumbnail != null) {
//				addThumbnail(headLine, thumbnail);
				addShopImg(headLine, thumbnail);
			}
			try {
				// 往数据库里插入头条信息
				int effectedNum = headLineDao.insertHeadLine(headLine);
				if (effectedNum > 0) {
//					deleteRedis4HeadLine();
					return new HeadLineExecution(HeadLineStateEnum.SUCCESS, headLine);
				} else {
					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new RuntimeException("添加头条信息失败:" + e.toString());
			}
		} else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public HeadLineExecution modifyHeadLine(HeadLine headLine, ImageHolder thumbnail) {
		// 空值判断，主要是判断头条Id是否为空
		if (headLine.getLineId() != null && headLine.getLineId() > 0) {
			// 设定默认值
			headLine.setLastEditTime(new Date());
			if (thumbnail != null) {
				// 若需要修改的图片流不为空，则需要处理现有的图片
				HeadLine tempHeadLine = headLineDao.queryHeadLineById(headLine.getLineId());
				if (tempHeadLine.getLineImg() != null) {
					// 若原来是存储有图片的，则将原先图片删除
					ImgeUtil.deleteFileOrPath(tempHeadLine.getLineImg());
				}
				// 添加新的图片，并将新的图片相对路径设置到实体类里
//				addThumbnail(headLine, thumbnail);
				addShopImg(headLine, thumbnail);
			}
			try {
				// 更新头条信息
				int effectedNum = headLineDao.updateHeadLine(headLine);
				if (effectedNum > 0) {
//					deleteRedis4HeadLine();
					return new HeadLineExecution(HeadLineStateEnum.SUCCESS, headLine);
				} else {
					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new RuntimeException("更新头条信息失败:" + e.toString());
			}
		} else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public HeadLineExecution removeHeadLine(long headLineId) {
		// 空值判断，主要判断头条Id是否为非空
		if (headLineId > 0) {
			try {
				// 根据Id查询头条信息
				HeadLine tempHeadLine = headLineDao.queryHeadLineById(headLineId);
				if (tempHeadLine.getLineImg() != null) {
					// 若头条原先存有图片，则将该图片文件删除
					ImgeUtil.deleteFileOrPath(tempHeadLine.getLineImg());
				}
				// 删除数据库里对应的头条信息
				int effectedNum = headLineDao.deleteHeadLine(headLineId);
				if (effectedNum > 0) {
//					deleteRedis4HeadLine();
					return new HeadLineExecution(HeadLineStateEnum.SUCCESS);
				} else {
					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new RuntimeException("删除头条信息失败:" + e.toString());
			}
		} else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public HeadLineExecution removeHeadLineList(List<Long> headLineIdList) {
		// 空值判断
		if (headLineIdList != null && headLineIdList.size() > 0) {
			try {
				// 根据传入的id列表获取头条列表
				List<HeadLine> headLineList = headLineDao.queryHeadLineByIds(headLineIdList);
				for (HeadLine headLine : headLineList) {
					// 遍历头条列表，若头条的图片非空，则将图片删除
					if (headLine.getLineImg() != null) {
						ImgeUtil.deleteFileOrPath(headLine.getLineImg());
					}
				}
				// 批量删除数据库中的头条信息
				int effectedNum = headLineDao.batchDeleteHeadLine(headLineIdList);
				if (effectedNum > 0) {
//					deleteRedis4HeadLine();
					return new HeadLineExecution(HeadLineStateEnum.SUCCESS);
				} else {
					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new RuntimeException("删除头条信息失败:" + e.toString());
			}
		} else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}

	/**
	 * 存储图片
	 *
	 * @param headLine
	 * @param thumbnail
	 */
	private void addThumbnail(HeadLine headLine, ImageHolder thumbnail) {
		String dest = PathUtil.getHeadLineImagePath();
		String thumbnailAddr = ImgeUtil.generateNormalImg(thumbnail, dest);
		headLine.setLineImg(thumbnailAddr);
	}
//
//	/**
//	 * 移除跟实体类相关的redis key-value
//	 */
//	private void deleteRedis4HeadLine() {
//		String prefix = HLLISTKEY;
//		// 获取跟头条相关的redis key
//		Set<String> keySet = jedisKeys.keys(prefix + "*");
//		for (String key : keySet) {
//			// 逐条删除
//			jedisKeys.del(key);
//		}
//	}


	/**
	 * 功能描述:添加图片
	 *
	 * @param:
	 * @return:
	 **/
	private void addShopImg(HeadLine headLine, ImageHolder thumbnail) {

		//获取绝对路径
//        String dest = PathUtil.getShopImagePath(shop.getShopId());
		String dest = PathUtil.getShopCategoryPath();
		System.out.println("相对路径dest:" + dest);
		String headLineImgAddr = ImgeUtil.generateThumbnail(thumbnail, dest);
		System.out.println("照片相对路径shopImgAddr:" + headLineImgAddr);
		headLine.setLineImg(headLineImgAddr);
	}
}
