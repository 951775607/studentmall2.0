$(function () {
    var loginUrl = '/student/local/logincheck';
    var userType = getQueryString("usertype");
    var loginCount = 0;
    $("#submit2").click(function () {
        var userName = $("#username").val();
        var password = $("#psw").val();
        var verifyCodeActual = $("#j_captcha").val();
        var needVerify = true;
        // var needVerify = false;
        // if (loginCount >= 3) {
        //     if (!verifyCodeActual) {
        //         alert("请输入验证码");
        //         return;
        //     } else {
        //         needVerify = true;
        //     }
        // }


        $.ajax({
            url:loginUrl,
            async:false,
            cache:false,
            type:"post",
            dataType:'json',
            // type:"post",
            // async:false,
            // cache:false,
            // dataType:'json',
            data:{
                userName:userName,
                password:password,
                verifyCodeActual:verifyCodeActual,
                //需要验证
                needVerify:needVerify
            },
            success:function (data) {
                if (data.success) {
                    alert("登录成功");
                    if (userType == 1) {
                        //如果在前段展示系统则退回前段页面
                        window.location.href = '/student/frontend/index';
                    } else {
                        // window.location.href = 'student/shopadmin/shoplist';
                        window.location.href = '/student/shopadmin/shoplist';
                    }
                } else {
                    alert("登录失败：" + data.errMsg);
                    // loginCount++;
                    // if (loginCount >= 3) {
                    //     $("#veryfyPart").show();
                    // }
                }
            }
        });

    });

});