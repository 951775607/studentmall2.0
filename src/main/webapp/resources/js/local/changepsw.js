$(function() {
	var url = '/student/local/changelocalpwd';
    var usertype = getQueryString("usertype");
	$('#submit').click(function() {
		//获取账号
		var userName = $('#userName').val();
		//获取原密码
		var password = $('#password').val();
		//获取新密码
		var newPassword = $('#newPassword').val();
        var confirmPassword = $("#conffirmPassword").val();
        if (newPassword != confirmPassword) {
            alert("密码不一致！");
        }

		var formData = new FormData();
		formData.append('userName', userName);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		//获取验证码
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
                if (data.success) {
                    // $.toast('提交成功！');
                    alert("提交成功！");
                    if (usertype == 1) {
                        //如果是前端展示系统页面则自动退回到前端展示系统 首页
                        window.location.href = '/student/frontend/shoplist';
                    } else {
                        //如果用户是在店家管理系统页面则自动回退到店铺列表页面
                        window.location.href = '/student/shopadmin/shoplist';
                    }
                    // $.toast('提交失败！');
                } else {
                    alert("提交失败！" + data.errMsg);
                    $('#captcha_img').click();
                }
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/student/shopadmin/shoplist';
	});
});
