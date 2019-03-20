package com.lhq.studentmall.dao;

import com.lhq.studentmall.entity.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @ClassName: AreaDao
 * @Description: 区域DAO
 * @author Leon
 *
 */
@Mapper
public interface AreaDao {
    /**
     *
     * @Title: queryArea
     * @Description: 列出区域列表
     * @param @return
     * @return List<Area>
     * @throws
     */
    List<Area> queryArea();
}
