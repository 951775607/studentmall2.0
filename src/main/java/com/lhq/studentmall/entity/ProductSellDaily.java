package com.lhq.studentmall.entity;

import java.util.Date;

/**
 * @author Leon
 * @ClassName: ProductSellDaily
 * @Description: 顾客消费的商品映射，即店铺每天销量
 * @date 2019/3/20 14:42
 */
public class ProductSellDaily {
    //主键
    private Long productSellDailyId;
    //具体消费时间（天数）
    private Date createTime;
    //销量
    private Integer total;
    //商品信息实体类
    private Product product;
    //店铺信息实体类
    private Shop shop;

    @Override
    public String toString() {
        return "ProductSellDaily{" +
                "productSellDailyId=" + productSellDailyId +
                ", createTime=" + createTime +
                ", total=" + total +
                ", product=" + product +
                ", shop=" + shop +
                '}';
    }

    public Long getProductSellDailyId() {
        return productSellDailyId;
    }

    public void setProductSellDailyId(Long productSellDailyId) {
        this.productSellDailyId = productSellDailyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
