package com.lhq.studentmall.dao;//package com.lhq.student.dao;


import com.lhq.studentmall.StudentmallApplication;

import com.lhq.studentmall.entity.PersonInfo;
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
public class PersoninfoDaoTest {
    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void deletePersoninfo() {
        personInfoDao.deletePersonInfo(12L);
    }
}
