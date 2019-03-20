package com.lhq.studentmall.util;

import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.exceptions.ShopOperationException;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Leon
 * @ClassName: ImgeUtil
 * @Description: 图片工具类，处理上传的图片
 * @date 2019/3/3 14:20
 */
public class ImgeUtil {
    //通过线程获取当前系统的执行路径
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //定义时间类型格式
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
    //获取随机数
    private static final Random r = new Random();

    /**
     * 功能描述:处理缩略图
     *
     * @param: CommonsMultipartFile thumbnail Spring自带的文件流处理工具
     * @param: String targetAddr文件存储的路径
     * @return:
     **/
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {

        //随机生成文件名，避免上传重名的图片
        String realFileName = getRandomFileName();
//        String realFileName = PathUtil.getRandomFileName();
        //获取图片文件的类型
        String extension = getFileExtension(thumbnail.getImageName());
        //避免文件夹不存在，如果无则创建文件夹
        System.out.println("generateThumbnail照片存放文件夹targetAddr:"+targetAddr);
        makeDirPath(targetAddr);
        //拼接文件名
        String relativeAddr = targetAddr + realFileName + extension;
        System.out.println("generateThumbnail拼接文件名relativeAddr:"+relativeAddr);
        //文件最终的保存地址
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        System.out.println("generateThumbnail文件最终保存地址dest:" + dest);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/1.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            throw new ShopOperationException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }
//    public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr){
//        //随机生成文件名，避免上传重名的图片
////        String realFileName = getRandomFileName();
//        String realFileName = PathUtil.getRandomFileName();
//        //获取图片文件的类型
//        String extension = getFileExtension(thumbnail);
//        //避免文件夹不存在，如果无则创建文件夹
//        makeDirPath(targetAddr);
//        //拼接文件名
//        String relativeAddr = targetAddr + realFileName + extension;
//        //文件最终的保存地址
//        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
//        try {
//            Thumbnails.of(thumbnail.getInputStream()).size(200, 200)
//                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/3.jpg")), 0.25f)
//                    .outputQuality(0.8f).toFile(dest);
//
//        } catch (IOException e) {
//            throw new ShopOperationException("创建缩略图失败：" + e.toString());
//        }
//        return relativeAddr;
//    }

    /**
     * 功能描述:创建目标路径所涉及的目录，即/home/work/student/xxx.jpg
     *         那么 home work student这三个文件夹都得自动创建
     * @param: targetAddr
     * @return:
     **/
    private static void makeDirPath(String targetAddr) {
        //获取绝对路径
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        realFileParentPath = realFileParentPath.substring(0, realFileParentPath.length() - 1);
        //System.out.println("新建文件夹地址realFileParentPath:"+realFileParentPath);
        File dirPath = new File(realFileParentPath);
        //判断目标文件夹是否存在
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 功能描述:获取当前上传的文件扩展类型
     *
     * @param: thumbnail
     * @return:
     **/
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
//    private static String getFileExtension(CommonsMultipartFile cFile) {
//        //获取当前的文件名
//        String originalFileName = cFile.getOriginalFilename();
//        //取出扩展名并返回
//        return originalFileName.substring(originalFileName.lastIndexOf("."));
//    }

    /**
     * 功能描述:获取系统生成的随机文件名
     *          当前年月日小时分钟秒钟+五位随机数
     * @param:
     * @return:String nowTimeStr 随机文件名
     **/
    public static String getRandomFileName() {
        //获取随机五位数,0-99999
        int rannum = r.nextInt(89999) + 10000;
        //格式化当前的时间
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }


    /**
     * 功能描述:测试方法
     *
     * @param:
     * @return:
     **/
    public static void main(String[] args) throws IOException {
        //Positions.BOTTOM_RIGHT水印位置
        //ImageIO.read(new File(basePath + "/3.jpg")), 0.25f)水印图片的地址，透明度
        //outputQuality(0.8f).toFile("F:\\JavaCode\\graduation-design\\img\\2-1.jpg");压缩比例以及输出地址
        Thumbnails.of(new File("F:\\JavaCode\\graduation-design\\img\\2.jpg"))
                .size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/3.jpg")), 0.25f)
                .outputQuality(0.8f).toFile("F:\\JavaCode\\graduation-design\\img\\2-1.jpg");
    }

    /**
     * 功能描述:删除图片
     *          storePath是文件路径还是目录路径
     *          如果是文件路径则删除文件
     *          如果是目录路径则删除目录下的所有文件
     * @param: storePath
     * @return:
     **/
    public static void deleteFileOrPath(String srorePath) {
        String s = PathUtil.getImgBasePath() + srorePath;
        System.out.println("deleteFileOrPath删除的目录地址：" + s);
        File fileOrPath = new File(PathUtil.getImgBasePath() + srorePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    /**
     * 功能描述:处理详情图，并返回生成图片的相对值路径
     *
     * @param:
     * @return:
     **/
    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        //获取不重复的随机名
        String realFileName = PathUtil.getRandomFileName();
        //获取文件的拓展名
        String extension = getFileExtension(thumbnail.getImageName());
        //如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        //获取文件存储的相对路径（带文件名）
        String relativeAddr = targetAddr + realFileName + extension;
        System.out.println("relativeAddr文件存储的相对路径：" + relativeAddr);
        //获取文件要保存到的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        System.out.println("relativeAddr文件存储的最终路径：" + PathUtil.getImgBasePath() + relativeAddr);
        //调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/3.jpg")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            throw new ShopOperationException("创建缩略图失败：" + e.toString());
        }

        return relativeAddr;
    }
}
