$(function () {
    //从url获取productId
    var productId = getQueryString('productId');
    //获取商品信息的url地址
    var productUrl = '/student/frontend/listproductdetailpageinfo?productId='
        + productId;
    //访问后台获取该商品的信息并渲染
    $.getJSON(productUrl, function (data) {
        if (data.success) {
            //获取商品信息
            var product = data.product;
            //给商品信息相关的html控件赋值
            //商品缩略图
            $('#product-img').attr('src', getContextPath()+product.imgAddr);
            //商品更新时间
            $('#product-time').text(
                new Date(product.lastEditTime)
                    .Format("yyyy-MM-dd"));
            //商品积分
            // if (product.point != undefined) {
            //     $("#product-point").text('消费可得' + product.point + '积分');
            // }

            //商品名
            $('#product-name').text(product.productName);
            //商品简介
            $('#product-desc').text(product.productDesc);
            //商品价格展示逻辑，主要判断原价现价是否为空，所有都为空则不显示价格栏目
            if (product.normalPrice != undefined && product.promotionPrice != undefined) {
                //如果现价和原价都不为空则都显示，并且给原价加删除的符号
                $("#price").show();
                $("#normal-Price").html('<del>' + '￥' + product.normalPrice + '</del>');
                $("#promotion-price").text('￥' + product.promotionPrice);

            } else if (product.normalPrice != undefined && product.promotionPrice != undefined) {
                //如果现价不为空而原价为空则只展示现价
                $("#promotion-price").text('￥' + product.promotionPrice);
            }
            var imgListHtml = '';
            //遍历商品详情图列表，并生成批量的img标签
            product.productImgList.map(function (item, index) {
                imgListHtml += '<div> <img src="' + getContextPath() + item.imgAddr
                    + '"width="100%"/></div>';
            });
           // 生成购买商品的二维码供商家扫描
           //  imgListHtml += '<div> <img src="/student/frontend/generateqrcode4product?productId='
            //     + product.productId + '"/></div>';
            $('#imgList').html(imgListHtml);
        }
    });
    //点击打开侧边栏
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();

});
