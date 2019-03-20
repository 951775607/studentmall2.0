package com.lhq.studentmall.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Leon
 * @ClassName: LocalController
 * @Description: TODO
 * @date 2019/3/18 17:21
 */
@Controller
@RequestMapping("/local")
public class LocalController {
    //绑定账号页路由
    @RequestMapping(value = "/accountbind", method = RequestMethod.GET)
    private String accountbind() {
        return "local/accountbind";
    }

    //修改密码页
    @RequestMapping(value = "/changepsw", method = RequestMethod.GET)
    private String changepsw() {
        return "local/changepsw";
    }

    //登录页路由
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String login() {
        return "local/login";
    }
}
