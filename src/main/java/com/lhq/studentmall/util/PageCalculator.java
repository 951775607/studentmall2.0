package com.lhq.studentmall.util;

/**
 * @author Leon
 * @ClassName: PageCalculator
 * @Description: 分页转换工具类
 * @date 2019/3/4 20:19
 */
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        //如果开始页是>0的，则开始的条数是n-1*页大小，如果是第0页，则从第0条开始
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
