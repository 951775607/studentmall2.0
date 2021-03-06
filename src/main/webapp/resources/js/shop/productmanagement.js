$(function () {
    //获取店铺下的商品列表URL
    var listUrl = '/student/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=9999';
    //商品下架url
    var statusUrl = '/student/shopadmin/modifyproduct';
    //删除商品
    var deleteUrl = '/student/shopadmin/deleteproduct';
    getList();

    /**
     * 获取此店铺下的商品列表
     */
    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var productList = data.productList;
                var tempHtml = '';
                //遍历每条商品信息，拼接成一行显示，列信息包括：商品名称，权重，上架/下架（包含productID），编辑按钮（productID）  预览（包含productID）
                productList.map(function (item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        //如果状态为0，表明是已下架的商品，操作变为上架（即点击上架按钮上架相关商品）
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    //拼接每件商品的行信息
                    tempHtml += '' + '<div class="row row-product">'
                        + '<div class="col-33">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-33">'
                        + item.point
                        + '</div>'
                        + '<div class="col-33">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">编辑      </a>'
                        //上下架
                        + '<a href="#" class="status" data-id="'
                        + item.productId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="delete" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">      删除</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">      预览</a>'
                        + '</div>'
                        + '<hr>'
                        + '</div>';
                });
                // tempHtml +=''+'<hr>'
                //将拼接好的信息赋值进html控件中
                $('.product-wrap').html(tempHtml);
            }
        });
    }


    //删除方法
    function deleteItem(id) {
        var product = {};
        product.productId = id;
        $.confirm('确定么?', function () {
            $.ajax({
                url: deleteUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }

    //将class为product-warp里面的a标签绑定上点击事件
    $('.product-wrap').on('click', 'a',
        function (e) {
            var target = $(e.currentTarget);
            if (target.hasClass('edit')) {
                //如果有class edit则点击进入店铺信息编辑页面，并带有productID参数
                window.location.href = '/student/shopadmin/productoperation?productId='
                    + e.currentTarget.dataset.id;
            } else if (target.hasClass('status')) {
                //如果有class status则调用后台功能上/下架相关商品，并带有productId参数
                changeItemStatus(e.currentTarget.dataset.id,
                    e.currentTarget.dataset.status);
            } else if (target.hasClass('delete')) {
                deleteItem(e.currentTarget.dataset.id);
            } else if (target.hasClass('preview')) {
                //如果有class preview则去前台展示该商品详情页预览商品情况
                window.location.href = '/student/shopadmin/productdetail?productId='
                    + e.currentTarget.dataset.id;
            }
        });

    //上下架操作
    function changeItemStatus(id, enableStatus) {
        //定义product json对象并添加productID以及状态（上下架）
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm("确定么？", function () {
            //上下架相关商品
            $.ajax({
                url: statusUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });

        });
    }

    $('#new').click(function () {
        window.location.href = '/student/shopadmiin/productedit';
    });
});