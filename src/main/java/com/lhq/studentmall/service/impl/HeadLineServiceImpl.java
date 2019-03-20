package com.lhq.studentmall.service.impl;


import com.lhq.studentmall.dao.HeadLineDao;
import com.lhq.studentmall.entity.HeadLine;
import com.lhq.studentmall.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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

//	@Override
//	@Transactional
//	public HeadLineExecution addHeadLine(HeadLine headLine,
//										 CommonsMultipartFile thumbnail) {
//		if (headLine != null) {
//			headLine.setCreateTime(new Date());
//			headLine.setLastEditTime(new Date());
//			if (thumbnail != null) {
//				addThumbnail(headLine, thumbnail);
//			}
//			try {
//				int effectedNum = headLineDao.insertHeadLine(headLine);
//				if (effectedNum > 0) {
//					String prefix = HLLISTKEY;
//					Set<String> keySet = jedisKeys.keys(prefix + "*");
//					for (String key : keySet) {
//						jedisKeys.del(key);
//					}
//					return new HeadLineExecution(HeadLineStateEnum.SUCCESS,
//							headLine);
//				} else {
//					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
//				}
//			} catch (Exception e) {
//				throw new RuntimeException("添加区域信息失败:" + e.toString());
//			}
//		} else {
//			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
//		}
//	}
//
//	@Override
//	@Transactional
//	public HeadLineExecution modifyHeadLine(HeadLine headLine,
//			CommonsMultipartFile thumbnail) {
//		if (headLine.getLineId() != null && headLine.getLineId() > 0) {
//			headLine.setLastEditTime(new Date());
//			if (thumbnail != null) {
//				HeadLine tempHeadLine = headLineDao.queryHeadLineById(headLine
//						.getLineId());
//				if (tempHeadLine.getLineImg() != null) {
//					FileUtil.deleteFile(tempHeadLine.getLineImg());
//				}
//				addThumbnail(headLine, thumbnail);
//			}
//			try {
//				int effectedNum = headLineDao.updateHeadLine(headLine);
//				if (effectedNum > 0) {
//					String prefix = HLLISTKEY;
//					Set<String> keySet = jedisKeys.keys(prefix + "*");
//					for (String key : keySet) {
//						jedisKeys.del(key);
//					}
//					return new HeadLineExecution(HeadLineStateEnum.SUCCESS,
//							headLine);
//				} else {
//					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
//				}
//			} catch (Exception e) {
//				throw new RuntimeException("更新头条信息失败:" + e.toString());
//			}
//		} else {
//			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
//		}
//	}
//
//	@Override
//	@Transactional
//	public HeadLineExecution removeHeadLine(long headLineId) {
//		if (headLineId > 0) {
//			try {
//				HeadLine tempHeadLine = headLineDao
//						.queryHeadLineById(headLineId);
//				if (tempHeadLine.getLineImg() != null) {
//					FileUtil.deleteFile(tempHeadLine.getLineImg());
//				}
//				int effectedNum = headLineDao.deleteHeadLine(headLineId);
//				if (effectedNum > 0) {
//					String prefix = HLLISTKEY;
//					Set<String> keySet = jedisKeys.keys(prefix + "*");
//					for (String key : keySet) {
//						jedisKeys.del(key);
//					}
//					return new HeadLineExecution(HeadLineStateEnum.SUCCESS);
//				} else {
//					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
//				}
//			} catch (Exception e) {
//				throw new RuntimeException("删除头条信息失败:" + e.toString());
//			}
//		} else {
//			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
//		}
//	}
//
//	@Override
//	@Transactional
//	public HeadLineExecution removeHeadLineList(List<Long> headLineIdList) {
//		if (headLineIdList != null && headLineIdList.size() > 0) {
//			try {
//				List<HeadLine> headLineList = headLineDao
//						.queryHeadLineByIds(headLineIdList);
//				for (HeadLine headLine : headLineList) {
//					if (headLine.getLineImg() != null) {
//						FileUtil.deleteFile(headLine.getLineImg());
//					}
//				}
//				int effectedNum = headLineDao
//						.batchDeleteHeadLine(headLineIdList);
//				if (effectedNum > 0) {
//					String prefix = HLLISTKEY;
//					Set<String> keySet = jedisKeys.keys(prefix + "*");
//					for (String key : keySet) {
//						jedisKeys.del(key);
//					}
//					return new HeadLineExecution(HeadLineStateEnum.SUCCESS);
//				} else {
//					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
//				}
//			} catch (Exception e) {
//				throw new RuntimeException("删除头条信息失败:" + e.toString());
//			}
//		} else {
//			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
//		}
//	}
//
//	private void addThumbnail(HeadLine headLine, CommonsMultipartFile thumbnail) {
//		String dest = FileUtil.getHeadLineImagePath();
//		String thumbnailAddr = ImageUtil.generateNormalImg(thumbnail, dest);
//		headLine.setLineImg(thumbnailAddr);
//	}

}
