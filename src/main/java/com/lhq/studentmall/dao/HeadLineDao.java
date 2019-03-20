package com.lhq.studentmall.dao;


import com.lhq.studentmall.entity.HeadLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HeadLineDao {

    /**
     * 根据传入查询条件（头条名查询头条）
     *
     * @return
     */
    List<HeadLine> queryHeadLine(
            @Param("headLineCondition") HeadLine headLineCondition);

    /**
     * 根据头条id返回唯一的头条信息
     *
     * @param lineId
     * @return
     */
    HeadLine queryHeadLineById(long lineId);

    /**
     * @param lineIdList
     * @return
     */
    List<HeadLine> queryHeadLineByIds(List<Long> lineIdList);

    /**
     * @param headLine
     * @return
     */
    int insertHeadLine(HeadLine headLine);

    /**
     * @param headLine
     * @return
     */
    int updateHeadLine(HeadLine headLine);

    /**
     * @param headLineId
     * @return
     */
    int deleteHeadLine(long headLineId);

    /**
     * @param lineIdList
     * @return
     */
    int batchDeleteHeadLine(List<Long> lineIdList);
}
