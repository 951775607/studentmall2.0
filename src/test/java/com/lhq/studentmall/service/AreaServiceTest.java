package com.lhq.studentmall.service;//package com.lhq.student.service;

import com.lhq.studentmall.StudentmallApplication;
import com.lhq.studentmall.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Leon
 * @ClassName: AreaServiceTest
 * @Description: AreaService测试类
 * @date 2019/3/2 23:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentmallApplication.class)
public class AreaServiceTest{
    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
//        assertEquals("区域2",areaList.get(0).getAreaName());
    }

}
