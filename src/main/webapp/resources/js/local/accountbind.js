$(function () {
    //绑定账号的controller
    var bindUrl = '/student/local/bindlocalauth';
    var userType = getQueryString('usertype');
    $("#submit").click(function () {
        //获取输入的账号
        var userName = $("#username").val();
        //获取输入的密码
        var password = $("#psw").val();
        //获取输入的验证码
        var verifyCodeActual = $("#j_captcha").val();
        var needVerify = false;
        if (!verifyCodeActual) {
            alert("请输入验证码！")
            return;
        }
        $.ajax({
            url:bindUrl,
            async:false,
            cache:false,
            type:"post",
            dataType:'json',
            data:{
                userName:userName,
                password:password,
                verifyCodeActual:verifyCodeActual
            },
            success:function (data) {
                if (data.success) {
                    alert("绑定成功！");
                    if (userType == 1) {
                        //如果在前段展示系统则退回前段页面
                        window.location.href = '/student/frontend/index';
                    } else {
                        // window.location.href = 'student/shopadmin/shoplist';
                        window.href = '/student/shopadmin/shoplist';
                    }
                } else {
                    alert("提交失败:" + data.errMsg);
                    $("#captcha_img").click();
                }

            }
        })
    });
})