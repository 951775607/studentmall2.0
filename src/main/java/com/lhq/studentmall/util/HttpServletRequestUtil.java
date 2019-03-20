package com.lhq.studentmall.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leon
 * @ClassName: HttpServletRequestUtil
 * @Description: request请求工具类
 * @date 2019/3/3 21:06
 */
public class HttpServletRequestUtil {

    /**
     * 功能描述:从request中获取key值并转换为整形
     *
     * @param: request
     * @param: key
     * @return: int
     **/
    public static int getInt(HttpServletRequest request, String key) {
        try {
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 功能描述:从request中获取key值并转换为长整形
     *
     * @param: request
     * @param: key
     * @return: Long
     **/
    public static Long getLong(HttpServletRequest request, String key) {
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1L;
        }
    }

    /**
     * 功能描述:从request中获取key值并转换为double类型
     *
     * @param: request
     * @param: key
     * @return: Double
     **/
    public static Double getDouble(HttpServletRequest request, String key) {

        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1d;
        }
    }

    /**
     * 功能描述:从request中获取key值并转换为Boolean类型
     *
     * @param: request
     * @param: key
     * @return: Boolean
     **/
    public static Boolean getBoolean(HttpServletRequest request, String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 功能描述:从request中获取key值并转换为去除前后空格的String类型
     *
     * @param: request
     * @param: key
     * @return: String
     **/
    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if (result != null) {
                //去除两端空格
                result = result.trim();
            }
            if ("".equals(result))
                result = null;
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
