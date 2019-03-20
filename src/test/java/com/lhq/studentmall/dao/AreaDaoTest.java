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


}
