package com.lhq.studentmall.dao;//package com.lhq.student.dao;


import com.lhq.studentmall.StudentmallApplication;
import com.lhq.studentmall.entity.Area;
import com.lhq.studentmall.entity.HeadLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Date 2019/3/2 23:09
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentmallApplication.class)
public class HeadLineDaoTest {

    @Autowired
    public HeadLineDao headLineDao;

    @Test
    public void queryHeadLine() {
        HeadLine headLine = new HeadLine();
        headLine.setLineId(1L);
        headLineDao.queryHeadLine(headLine);
    }

    @Test
    public void queryHeadLineById() {
        HeadLine headLine = headLineDao.queryHeadLineById(1L);
        System.out.println(headLine.getLineName());

    }

    @Test
    public void queryHeadLineByIds() {
        List<Long> lineIdList = new ArrayList<>();
        lineIdList.add(1L);
        lineIdList.add(2L);
        lineIdList.add(3L);
        List<HeadLine> headLines = headLineDao.queryHeadLineByIds(lineIdList);
        System.out.println(headLines.size());

    }

    @Test
    public void insertHeadLine() {
        HeadLine headLine = new HeadLine();
        headLine.setLineName("头条14");
        headLine.setEnableStatus(1);
        headLine.setCreateTime(new Date());
        headLine.setLastEditTime(new Date());
        headLine.setPriority(15);
        headLine.setLineLink("fsdfdsfsdfsdfads.html");
        headLine.setLineImg("图片吧");
        headLineDao.insertHeadLine(headLine);
    }

    @Test
    public void updateHeadLine() {
        HeadLine headLine = new HeadLine();
        headLine.setLineId(5L);
        headLine.setLineName("头条15");
        headLine.setEnableStatus(1);
        headLine.setCreateTime(new Date());
        headLine.setLastEditTime(new Date());
        headLine.setPriority(10);
        headLine.setLineLink("fsdfdsfsdfsdfads.html");
        headLine.setLineImg("图片吧");
        headLineDao.updateHeadLine(headLine);

    }

    @Test
    public void deleteHeadLine() {
        headLineDao.deleteHeadLine(10L);
    }

    @Test
    public void batchDeleteHeadLine() {
        List<Long> lineIdList = new ArrayList<>();
        lineIdList.add(5L);
        lineIdList.add(6L);
        lineIdList.add(7L);
        lineIdList.add(8L);
        lineIdList.add(9L);
        headLineDao.batchDeleteHeadLine(lineIdList);
    }




}
