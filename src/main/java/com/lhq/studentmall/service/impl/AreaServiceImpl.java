package com.lhq.studentmall.service.impl;

import com.lhq.studentmall.dao.AreaDao;
import com.lhq.studentmall.entity.Area;
import com.lhq.studentmall.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
