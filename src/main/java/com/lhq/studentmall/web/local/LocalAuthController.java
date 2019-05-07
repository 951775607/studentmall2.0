package com.lhq.studentmall.web.local;


import com.lhq.studentmall.entity.LocalAuth;
import com.lhq.studentmall.entity.PersonInfo;
import com.lhq.studentmall.enume.LocalAuthStateEnum;
import com.lhq.studentmall.exceptions.LocalAuthExecution;
import com.lhq.studentmall.service.LocalAuthService;
import com.lhq.studentmall.util.CodeUtil;
import com.lhq.studentmall.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leon
 * @ClassName: LocalAuthController
 * @Description: TODO
 * @date 2019/3/18 16:18
 */
@Controller
@RequestMapping(value = "/local", method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAuthController {
    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 功能描述:将用户信息与平台账号绑定
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入不正确");
            return modelMap;
        }
        //获取输入的账号密码
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        //从seeion中获取当前用户信息，通过微信登录就有信息
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (userName != null && password != null && user != null && user.getUserId() != null) {
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUserName(userName);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(user);
            //绑定账号
            LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
            if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", le.getStateInfo());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码不能为空!");
        }
        return modelMap;
    }

    /**
     * 功能描述:修改密码
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入不正确");
            return modelMap;
        }
        //获取账号
        String userName = HttpServletRequestUtil.getString(request, "userName");

        //获取原密码
        String password = HttpServletRequestUtil.getString(request, "password");

        //获取新密码
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");

        //获取当前用户信息，一旦通过微信登录就能获取
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        //账号新旧密码，当前用户以及新旧密码不能相同
        if (userName != null && password != null && newPassword != null && user != null && user.getUserId() != null && !password.equals(newPassword)) {
            try {
                //查看原来的账号，查看输入的账号是否一致，不然是非法操作
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
                if (localAuth == null || !localAuth.getUserName().equals(userName)) {
                    //退出
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "当前登录账号不一致！");
                    return modelMap;
                }
                //修改密码
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入密码！");
        }
        return modelMap;
    }


    /**
     * 功能描述:登录验证
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logincheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取是否需要验证
        Boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入不正确");
            return modelMap;
        }
        //获取账号
        String userName = HttpServletRequestUtil.getString(request, "userName");
        //获取原密码
        String password = HttpServletRequestUtil.getString(request, "password");
        //获取新密码
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        //获取当前用户信息，一旦通过微信登录就能获取
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        //账号新旧密码，当前用户以及新旧密码不能相同
        if (userName != null && password != null) {
            //根据传入的账号密码去获取平台信息
            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
            if (localAuth != null) {
                //获取到账号则登录成功
                modelMap.put("success", true);
                //在seeion里设置用户信息
                request.getSession().setAttribute("user", localAuth.getPersonInfo());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误！");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码不能为空！");
        }
        return modelMap;
    }

    /**
     * 功能描述:退出登录
     *
     * @param:
     * @return:
     **/
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logout(HttpServletRequest request,
                                       HttpServletResponse response) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //清空session用户数据
        request.getSession().setAttribute("user", null);
//        request.getSession().setAttribute("shopList", null);
//        request.getSession().setAttribute("currentShop", null);
        modelMap.put("success", true);
        return modelMap;
    }

}

