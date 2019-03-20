$(function () {
    //是否正在加载
    var loading = false;
    //分页允许返回最大条数，超过则禁止访问后台
    var maxItems = 999;
    //一页返回的最大条数
    var pageSize = 10;
    //获取店铺列表url
    var listUrl = '/student/frontend/listshops';
    //获取店铺类别列表以及区域列表的url
    var searchDivUrl = '/student/frontend/listshopspageinfo';
    //页码
    var pageNum = 1;
    //从地址中获取parent shop category id
    var parentId = getQueryString('parentId');
    var areaId = '';
    var shopCategoryId = '';
    var shopName = '';


    //渲染出店铺类别列表以及区域列表以供搜索
    getSearchDivData();

    /**
     * 获取店铺类别列表以及区域列表信息
     */
    function getSearchDivData() {
        //如果传入了parentID，则取出此一级类别下面的所有二级类别
        var url = searchDivUrl + '?' + 'parentId=' + parentId;
        $.getJSON(
            url,
            function (data) {
                if (data.success) {
                    //获取后台返回过来的店铺类别列表
                    var shopCategoryList = data.shopCategoryList;
                    var html = '';
                    html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
                    //遍历店铺类别列表，拼接出a标签
                    shopCategoryList.map(function (item, index) {
                        html += '<a href="#" class="button" data-category-id='
                            + item.shopCategoryId
                            + '>'
                            + item.shopCategoryName
                            + '</a>';
                    });
                    //将拼接好的类别标签嵌入前台的html组件中
                    $('#shoplist-search-div').html(html);
                    var selectOptions = '<option value="">全部街道</option>';
                    //获取后台返回过来的区域信息列表
                    var areaList = data.areaList;
                    //遍历区域信息列表，拼接出option标签集
                    areaList.map(function (item, index) {
                        selectOptions += '<option value="'
                            + item.areaId + '">'
                            + item.areaName + '</option>';
                    });
                    //将标签集添加进area列表里
                    $('#area-search').html(selectOptions);
                }
            });
    }

    /**
     * 获取分页展示的店铺列表信息
     * @param pageSize
     * @param pageIndex
     */
    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去获取
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&parentId=' + parentId + '&areaId=' + areaId
            + '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
        //是否加载信息，若还在后台获取数据则不能再次访问后台，避免多次重复提交
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                //获取当前查询条件下店铺的总数
                maxItems = data.count;
                var html = '';
                //遍历店铺列表，拼接出卡片集合
                data.shopList.map(function (item, index) {
                    html += '' + '<div class="card" data-shop-id="'
                        + item.shopId + '">' + '<div class="card-header">'
                        + item.shopName + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        +  getContextPath()+item.shopImg + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.shopDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.list-div').append(html);
                //获取目前为止以显示的卡片总数，包含之前已经加载的
                var total = $('.list-div .card').length;
                //如果总数达到跟按照此查询条件列出来的总数一致，则停止加载
                if (total >= maxItems) {
                    // 加载完毕，则注销无限加载事件，以防不必要的加载
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    // 删除加载提示符
                    $('.infinite-scroll-preloader').remove();
                }
                //否则页码+1
                pageNum += 1;
                //加载结束，可以继续加载
                loading = false;
                //刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        });
    }

    // 预先加载20条
    addItems(pageSize, pageNum);

    //下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    //点击店铺的卡片进入该店铺的详情页
    $('.shop-list').on('click', '.card', function (e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = '/student/frontend/shopdetail?shopId=' + shopId;
    });

    //选择新的店铺类别之后，重置页码，清空原先的店铺列表，按照新的类别去查询
    $('#shoplist-search-div').on(
        'click',
        '.button',
        function (e) {
            if (parentId) {// 如果传递过来的是一个父类下的子类
                shopCategoryId = e.target.dataset.categoryId;
                //如果之前已经选定了别的category，则移除其选定效果，改成选定新的
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    shopCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                //由于查询条件改变，清空店铺列表再进行查询
                $('.list-div').empty();
                //重置页码
                pageNum = 1;
                addItems(pageSize, pageNum);
            } else {// 如果传递过来的父类为空，则按照父类查询
                parentId = e.target.dataset.categoryId;
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    parentId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                //清空列表
                $('.list-div').empty();
                //重置页码
                pageNum = 1;
                addItems(pageSize, pageNum);
                parentId = '';
            }

        });

    //需要查询的店铺名字发生改变后，重置页码，清空原先的店铺列表，按照新的输出
    $('#search').on('input', function (e) {
        shopName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    //区域信息发生变化后，重置页码，清空原先的店铺列表，按照新的区域去查询
    $('#area-search').on('change', function () {
        areaId = $('#area-search').val();
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    //点击打开右侧栏
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });

    //初始化页面
    $.init();
});
