package com.lhq.studentmall.dao;//package com.lhq.student.dao;


import com.lhq.studentmall.StudentmallApplication;
import com.lhq.studentmall.entity.Area;
import com.lhq.studentmall.entity.ShopAuthMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @Description TODO
 * @Date 2019/3/2 23:09
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentmallApplication.class)
public class AreaDaoTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea() {
        List<Area> areaList = areaDao.queryArea();
    }

    @Test
    public void insertArea() {
        Area area = new Area();
        area.setAreaName("旺村");
        area.setCreateTime(new Date());
        area.setLastEditTime(new Date());
        area.setPriority(5);
        areaDao.insertArea(area);
    }


    @Test
    public void updateArea() {
        Area area = new Area();
        area.setAreaId(6);
        area.setAreaName("员村");
        area.setCreateTime(new Date());
        area.setLastEditTime(new Date());
        area.setPriority(10);
        areaDao.updateArea(area);
    }

    @Test
    public void deleteArea() {
        areaDao.deleteArea(6);
    }

    @Test
    public void batchDeleteArea() {
        List<Long> areaIdList = new ArrayList<>();
        areaIdList.add(7L);
        areaIdList.add(8L);

        areaDao.batchDeleteArea(areaIdList);
    }


}
