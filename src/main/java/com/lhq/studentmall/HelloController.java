package com.lhq.studentmall;

import com.lhq.studentmall.dao.AreaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leon
 * @ClassName: HelloController
 * @Description: TODO
 * @date 2019/3/20 16:05
 */
@RestController
public class HelloController {
    @RequestMapping(value = "/student",method = RequestMethod.GET)

    @Autowired

    public String hello() {
        return "Welcome to studentmall!";
    }
}
