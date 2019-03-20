/*
* 获取店铺分类以及区域信息
* */
$(function () {
    var shopId = getQueryString("shopId");
    //判断shop是否存在，存在在更新，否则新增
    var isEdit = shopId ? true : false;

    //初始化路径
    //查询店铺分类
    var initUrl = '/student/shopadmin/getshopinitinfo';
    //注册店铺
    var registerShopUrl = '/student/shopadmin/registershop';
    //根据id查找店铺
    var shopInfoUrl = '/student/shopadmin/getshopbyid?shopId=' + shopId;
    //修改店铺
    var editShopUrl = '/student/shopadmin/modifyshop';


    // alert(initUrl);
    if (!isEdit) {
        getShopInitInfo();
    } else {
        getShopInfo(shopId);
    }
    //查询店铺信息并回显
    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);

                //双重否定是肯定
                var shopCategory = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '" selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>'
                });
                $('#shop-category').html(shopCategory);
                //不能选择
                $('#shop-category').attr('disabled','disabled');
                $('#area').html(tempAreaHtml);
                //默认选中
                $('#area').attr('data-id',shop.areaId);
                // $("#area option[]='"+shop.area.areaId+"']").attr("selected","selected");
            }
        });
    }


    //获取店铺分类以及区域分类
    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                //遍历店铺分类
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">'
                        + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        });
    }
    $('#submit').click(function () {
        var shop = {};
        if (isEdit) {
            shop.shopId = shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();
        //双重否定是肯定
        shop.shopCategory = {
            shopCategoryId: $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId: $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        //流文件传输
        var shopImg = $('#shop-img')[0].files[0];
        var formData = new FormData();
        formData.append('shopImg', shopImg);
        formData.append('shopStr', JSON.stringify(shop));
        //更换验证码
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码');
            return;
        }
        formData.append('verifyCodeActual', verifyCodeActual);
        $.ajax({
            url: (isEdit?editShopUrl:registerShopUrl),
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
                } else {
                    $.toast('提交失败！' + data.errMsg);
                }
                $('#captcha_img').click()
            }

        });
    })

});


