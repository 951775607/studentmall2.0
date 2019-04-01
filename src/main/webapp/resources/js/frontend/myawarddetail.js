$(function() {
	// 从地址栏中获取userAwardId
	var userAwardId = getQueryString('userAwardId');
	// 根据userAwardId获取用户奖品映射信息
	var awardUrl = '/student/frontend/getawardbyuserawardid?userAwardId='
			+ userAwardId;
    qrious();
	$
			.getJSON(
					awardUrl,
					function(data) {
						if (data.success) {
							// 获取奖品信息并显示
							var award = data.award;
							$('#award-img').attr('src', getContextPath() + award.awardImg);
							$('#create-time').text(
									new Date(data.userAwardMap.createTime)
											.Format("yyyy-MM-dd"));
							$('#award-name').text(award.awardName);
							$('#award-desc').text(award.awardDesc);
							var imgListHtml = '';
							// 若未去实体店兑换实体奖品，生成兑换礼品的二维码供商家扫描
							if (data.usedStatus == 0) {
                                var status = data.usedStatus;
								// imgListHtml += '<div> <img src="/student/frontend/generateqrcode4award?userAwardId='
								// 		+ userAwardId
								// 		+ '" width="100%"/></div>';
								$('#imgList').html(imgListHtml);
							}
						}
					});
	// 若点击"我的"，则显示侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();


    // 若未去实体店兑换实体奖品，生成兑换礼品的二维码供商家扫描
    function qrious() {
        if (status == 0) {
            $.ajax({
                url: '/student/frontend/generateqrcode4award?userAwardId=' + userAwardId,
                type : "get",
                dataType : "json",
                success : function(data) {
                    if (data.success) {
                        //创建qrious对象
                        var qr = new QRious({
                            //指定那个元素
                            element: document.getElementById("qrious"),
                            //容错级别
                            level: "Q",
                            //大小
                            size: 250,
                            //值
                            value: data.quior
                        });
                    } else {
                        alert("生成二维码失败！");
                    }
                }
            });
        }
    }
});
