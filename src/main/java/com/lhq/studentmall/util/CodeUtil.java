package com.lhq.studentmall.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leon
 * @ClassName: CodeUtil
 * @Description: 验证码工具类
 * @date 2019/3/4 4:30
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        //系统生成的验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //输入的验证码
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");

        //不忽略大小写比较
//        if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
//            return false;
//        }
        //忽略大小写
        if (verifyCodeActual == null || !verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}
