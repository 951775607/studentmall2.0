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

    /**
     * 功能描述:插入区域信息
     *
     * @param:
     * @return:
     **/
    int insertArea(Area area);

    /**
     * 功能描述:更新区域信息
     *
     * @param:
     * @return:
     **/
    int updateArea(Area area);

    /**
     * 功能描述:删除区域信息
     *
     * @param:
     * @return:
     **/
    int deleteArea(long areaId);


    /**
     * 功能描述:批量删除区域信息
     *
     * @param:
     * @return:
     **/
    int batchDeleteArea(List<Long> areaIdList);
}
