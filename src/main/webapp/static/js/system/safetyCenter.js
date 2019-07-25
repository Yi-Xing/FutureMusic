//安全中心的js
$(document).ready(function () {

    //点击账号绑定页面的确定按钮
    $("#submitAccount").on("click", function () {
        var account=$(".zhanghao").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/bindingMailbox",
            data: {
                "mailbox": account
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    alert(data.information)
                    // $(".promptInformation").text(data.information);
                }else {
                    alert("邮箱:"+account+"绑定成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });

    // 用于提交原密码
    $("#submitOriginalPassword").on("click", function () {
        var originalPassword=$(".zhanghao").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/mailboxBindingVerifyOriginalPassword",
            data: {
                "originalPassword": originalPassword
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    alert(data.information)
                    // $(".promptInformation").text(data.information);
                }else {
                    // 密码正确进行页面跳转
                    window.location.href="/mailboxBindingPasswordChangePasswordPage";
                }
            }
        });
    });

    // 用于提交验证码
    $("#submitVerificationCode").on("click", function () {
        var verificationCode=$(".zhanghao").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/mailboxBindingMailboxVerificationCode",
            data: {
                "verificationCode": verificationCode
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    alert(data.information)
                    // $(".promptInformation").text(data.information);
                }else {
                    // 密码正确进行页面跳转
                    window.location.href="/mailboxBindingMailboxChangePasswordPage";
                }
            }
        });
    });

    // 邮箱验证的点击获取验证码执行
    $("#verificationCode").on("click", function () {
        $.ajax({
            type: "get",
            url: "/mailboxBindingSecretProtectionVerificationCode",
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===1){
                    alert("验证码发送成功");
                }else{
                    alert("验证码发送失败");
                }
            }
        });
    });

    // 用于提交新密码
    $("#submitNewPassword").on("click", function () {
        var password=$(".password").val();
        var passwordAgain=$(".passwordAgain").val();
        alert(password)
        alert(passwordAgain)
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/mailboxBindingChangePassword",
            data: {
                "password": password,
                "passwordAgain": passwordAgain
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    alert(data.information)
                    // $(".promptInformation").text(data.information);
                }else {
                    alert("密码修改成功")
                    // 密码正确进行页面跳转
                    window.location.href="/index";
                }
            }
        });
    });

});