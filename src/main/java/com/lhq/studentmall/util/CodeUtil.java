package com.lhq.studentmall.util;

import com.google.code.kaptcha.Constants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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


    /**
     * 功能描述:生成图片二维码
     *
     * @param:
     * @return:
     **/
    public static BitMatrix generateQRCodeStream(String content, HttpServletResponse resp) {
        // 给响应添加头部信息，主要是告诉浏览器返回的是图片流
        resp.setHeader("Cache-Control", "no-store");
        //设置不缓存，二维码定时过期
        resp.setHeader("Pragma", "no-cache");
        //设置图片流
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/png");
        // 设置图片的文字编码以及内边框距
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix;
        try {
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        return bitMatrix;

    }
}
