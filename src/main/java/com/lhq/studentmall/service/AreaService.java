package com.lhq.studentmall.service;


import com.lhq.studentmall.dto.AreaExecution;
import com.lhq.studentmall.entity.Area;

import java.util.List;

/**
 * @author Leon
 * @ClassName: AreaService
 * @Description: 区域service类
 */
public interface AreaService {
    /**
     * 功能描述:获取列表数据
     *
     * @param:
     * @return:
     **/
    List<Area> getAreaList();

    /**
     * 增加区域信息
     *
     * @param area
     * @return
     */
    AreaExecution addArea(Area area);

    /**
     * 修改区域信息
     *
     * @param area
     * @return
     */
    AreaExecution modifyArea(Area area);

    /**
     * 删除区域信息
     *
     * @param area
     * @return
     */
    int delArea(Integer area);

}
