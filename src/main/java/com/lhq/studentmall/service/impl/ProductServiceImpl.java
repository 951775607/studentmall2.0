package com.lhq.studentmall.service.impl;

import com.lhq.studentmall.dao.ProductDao;
import com.lhq.studentmall.dao.ProductImgDao;
import com.lhq.studentmall.dto.ImageHolder;
import com.lhq.studentmall.dto.ProductExecution;
import com.lhq.studentmall.entity.Product;
import com.lhq.studentmall.entity.ProductImg;
import com.lhq.studentmall.enume.ProductStateEnum;
import com.lhq.studentmall.exceptions.ProductOperationException;
import com.lhq.studentmall.service.ProductService;
import com.lhq.studentmall.util.ImgeUtil;
import com.lhq.studentmall.util.PageCalculator;
import com.lhq.studentmall.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Leon
 * @ClassName: ProductServiceImpl
 * @Description: TODO
 * @date 2019/3/5 3:24
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;


    /**
     * 功能描述:查询商品列表分页，可输入的条件有：商品名（模糊），商品状态，店铺ID，商品类别
     *
     * @param:
     * @return:
     **/
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //页码转换成数据库的行码，并调用dao层取回指定页码的商品列表
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        //基于同样的查询条件返回该查询条件下的商品总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    /**
     * 功能描述:通过商品id查询唯一的商品信息
     *
     * @param:
     * @return:
     **/
    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    /**
     * 功能描述:修改商品信息以及图片处理
     * 1.如果缩略图参数有值，则处理缩略图，如原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product
     * 2.如果商品详情图列表参数有值，则对商品详情图片列表进行同样的操作
     * 3.将tb_product_img下面的该商品原先的商品详情图记录全部删除
     * 4.更新tb_product的信息
     *
     * @param:
     * @return:
     **/
    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //设置修改时间
            product.setLastEditTime(new Date());
            //如果商品缩略图不为空且原有缩略图不为空则删除原有图片地址
            if (thumbnail != null) {
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImgeUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            //如果有新存入的商品详情图，则将原先的删除，并再添加新的图片
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductList(product, productImgHolderList);
            }
            //更新信息
            try {
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("更新商品信息失败:" + e.toString());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 删除商品
     *
     * @param
     * @return
     */
    @Override
    public void deleteProduct(Product productCondition) {
        productDao.deleteProduct(productCondition.getProductId(), productCondition.getShop().getShopId());
    }

    /**
     * 功能描述:删除某个商品下的所有详情图
     *
     * @param:
     * @return:
     **/
    private void deleteProductImgList(Long productId) {
        //根据productId获取原来的图片
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        //删除原来的图片
        for (ProductImg productImg : productImgList) {
            ImgeUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //删除数据库里原有的图片信息
        productImgDao.deleteProductImgByProductId(productId);
    }


    /**
     * 功能描述:处理缩略图，获取缩略图相对路径并赋值给product
     * 往tb_product写人商品信息，获取productId
     * 结合productID批量处理商品性情图
     * 将商品性情图表批量插入tb_product_img中
     *
     * @param:
     * @return:
     **/
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品设置默认属性，默认为上架状态
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            //如果商品缩略图不为空则添加
            if (thumbnail != null) {
                //添加商品缩略图
                addThumbnail(product, thumbnail);
            }
            try {
                //创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败:" + e.toString());
            }
            //如果商品详情图片不为空则添加
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                addProductList(product, productImgHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            //传参为空则返回空值错误
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }


    /**
     * 功能描述:批量添加商品详情图
     *
     * @param:
     * @return:
     **/
    private void addProductList(Product product, List<ImageHolder> productImgHolderList) {
        //获取图片存储路径，这里直接存放到相应的店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        //遍历图片一次去处理，并添加进productImg实体类中
        for (ImageHolder productImgHolder : productImgHolderList) {
            String imgAddr = ImgeUtil.generateNormalImg(productImgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        //如果确实有图片需要添加的，就执行批量添加操作
        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new RuntimeException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("创建商品详情图片失败:" + e.toString());
            }
        }
    }

    /**
     * 功能描述:添加商品缩略图
     *
     * @param: product
     * @return: thumbnail
     **/
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImgeUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }
}
