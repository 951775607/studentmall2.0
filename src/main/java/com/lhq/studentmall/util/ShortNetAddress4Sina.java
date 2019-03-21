package com.lhq.studentmall.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Leon
 * @ClassName: ShortNetAddress4Sina
 * @Description: TODO
 * @date 2019/3/21 21:26
 */
public class ShortNetAddress4Sina {
//    static String actionUrl = "http://api.t.sin.com.cn/short_url/shortrn.json";
    static String actionUrl = "http://api.t.sina.com.cn/short_url/shorten.json";
    static String APPKEY = "2815391962,31641035,3271760578,3925598208";

    public static void main(String[] args) {
        String longUrl = "http://baidu.com/student/frontend/index";
        System.out.println(getShortUrl(longUrl));
    }

    private static String getShortUrl(String longUrl) {
        longUrl = java.net.URLEncoder.encode(longUrl);
        String appkey = APPKEY;
        String[] sourceArray = appkey.split(",");
        for (String key : sourceArray) {
            String shortUrl = sinaShortUrl(key, longUrl);
            if (shortUrl != null) {
                return shortUrl;
            }
        }
        return null;
    }

    private static String sinaShortUrl(String source, String longUrl) {
        String result = sendPost(actionUrl, "url_long=" + longUrl + "&source=" + source);
        if (result == null || "".equals(result)) {
            return "";
        }
        JSONArray jsonArr = JSON.parseArray(result);
        JSONObject json = JSON.parseObject(jsonArr.get(0).toString());
        String ret = json.get("url_short").toString();
        return ret;
    }

    private static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.println(conn);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result +=line;
            }
        } catch (Exception e) {
            System.out.println("发送请求失败");
            e.printStackTrace();
        }finally {
            try{
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;

    }

}
