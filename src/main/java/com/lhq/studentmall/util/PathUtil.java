package com.lhq.studentmall.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Leon
 * @ClassName: PathUtil
 * @Description: 路径处理工具类.返回图片路径
 * @date 2019/3/3 14:41
 */
public class PathUtil {

    //获取系统分割符，因为windows和其他系统是不一样的。因此在运行的时候获取系统分隔符，然后替换
    private static String seperator = System.getProperty("file.separator");
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat(
            "yyyyMMddHHmmss"); // 时间格式化的格式
    private static final Random r = new Random();


    /**
     * 功能描述:获取上传图片的根路径
     *
     * @param:
     * @return: String basePath
     **/
    public static String getImgBasePath() {
        //获取当前运行系统名称
        String os = System.getProperty("os.name");
        String basePath = "";
        //判断是win系统还是ios或linux
        if (os.toLowerCase().startsWith("win")) {
//            basePath = " F:\\JavaCode\\graduation-design\\image\\";
            basePath = "F:/JavaCode/graduation-design";
        } else {
            basePath = "/home/student/image";
        }
        //替换系统运行符以应对不同的系统
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    /**
     * 功能描述:获取店铺图片地址的子路径
     *
     * @param: shopId
     * @return: shopImagePath
     **/
    public static String getShopImagePath(long shopId) {
        StringBuilder shopImagePathBuilder = new StringBuilder();
//        String imagePath = "/upload/item/shop/" + shopId;
//        imagePath = imagePath.replace("/", seperator);
//        return imagePath;
        shopImagePathBuilder.append("/upload/images/item/shop/");

        shopImagePathBuilder.append(shopId);
        shopImagePathBuilder.append("/");
        String shopImagePath = shopImagePathBuilder.toString().replace("/",
                seperator);
        System.out.println("shipImggePath1111:" + shopImagePath);
        return shopImagePath;
    }

    public static String getRandomFileName() {
        // 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
        int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
        String nowTimeStr = sDateFormat.format(new Date()); // 当前时间
        return nowTimeStr + rannum;
    }
}
