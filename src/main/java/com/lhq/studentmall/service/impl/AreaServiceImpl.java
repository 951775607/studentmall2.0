package com.lhq.studentmall.service.impl;

import com.lhq.studentmall.dao.AreaDao;
import com.lhq.studentmall.dto.AreaExecution;
import com.lhq.studentmall.entity.Area;
import com.lhq.studentmall.enume.AreaStateEnum;
import com.lhq.studentmall.exceptions.AreaOperationException;
import com.lhq.studentmall.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Leon
 * @ClassName: AreaServiceImpl
 * @Description: 区域service实现类
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }

    @Override
    public AreaExecution addArea(Area area) {
        // 空值判断，主要是判断areaName不为空
        if (area.getAreaName() != null && !"".equals(area.getAreaName())) {
            // 设置默认值
            area.setCreateTime(new Date());
            area.setLastEditTime(new Date());
            try {
                int effectedNum = areaDao.insertArea(area);
                if (effectedNum > 0) {
//                    deleteRedis4Area();
                    return new AreaExecution(AreaStateEnum.SUCCESS, area);
                } else {
                    return new AreaExecution(AreaStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new AreaOperationException("添加区域信息失败:" + e.toString());
            }
        } else {
            return new AreaExecution(AreaStateEnum.EMPTY);
        }
    }

    @Override
    public AreaExecution modifyArea(Area area) {
        // 空值判断，主要是areaId不为空
        if (area.getAreaId() != null && area.getAreaId() > 0) {
            // 设置默认值
            area.setLastEditTime(new Date());
            try {
                // 更新区域信息
                int effectedNum = areaDao.updateArea(area);
                if (effectedNum > 0) {
//                    deleteRedis4Area();
                    return new AreaExecution(AreaStateEnum.SUCCESS, area);
                } else {
                    return new AreaExecution(AreaStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new AreaOperationException("更新区域信息失败:" + e.toString());
            }
        } else {
            return new AreaExecution(AreaStateEnum.EMPTY);
        }
    }

    @Override
    public int delArea(Integer areaId) {
        int effectedNum = areaDao.deleteArea(areaId);
        return effectedNum;
    }

    /**
     * 移除跟实体类相关的redis key-value
     */
//    private void deleteRedis4Area() {
//        String key = AREALISTKEY;
//        // 若redis存在对应的key,则将key清除
//        if (jedisKeys.exists(key)) {
//            jedisKeys.del(key);
//        }
//    }
}
