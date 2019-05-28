// 首页登录注册和用户页面：
window.onload = function () {
    //登录注册篇---------------------------
    // 用于发送验证码
    $("#button4").on("click", function () {
        var registerMail = $("#registerMail").val();
        $.ajax({
            type: "get",
            url: "registerVerificationCode?mailbox=" + registerMail,
            dataType: "json",
            success: function (data) {
                alert(data);
                // alert(data.registerMail);
                if (data.state) {
                    // alert("我调用了");
                }
                $("#wc").text(data);
                document.getElementsByClassName('reg_hed_right')[0].children[1].innerHTML = data.information;
            }
        });
    });

    // 用于注册账号
    $("#registerUser").on("click", function () {
        var registerUserName = $("#registerUserName").val();
        var registerMail = $("#registerMail").val();
        var registerPassword = $("#registerPassword").val();
        var registerPasswordAgain = $("#registerPasswordAgain").val();
        var verificationCode = $("#verificationCode").val();
        var agreement = $("#agreement").is(":checked");
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "register",
            data: {
                "userName": registerUserName,
                "sendMail": registerMail,
                "password": registerPassword,
                "passwordAgain": registerPasswordAgain,
                "verificationCode": verificationCode,
                "agreement": agreement
            },
            dataType: "json",
            success: function (data, status) {
                $("#wc").text(data);
                if (data.state <= 0) {
                    document.getElementsByClassName('reg_hed_right')[0].children[1].innerHTML = data.information;
                } else {
                    alert("注册成功了");
                }
            }
        });
    });

    // 用于登录账号
    $("#registerUser").on("click", function () {
        // 账号
        var mailbox = $("#registerUserName").val();
        // 密码
        var password = $("#registerUserName").val();
        // 是否选中7天登录
        var automatic = $("#agreement").is(":checked");
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "loginAccount",
            data: {
                "mailbox": mailbox,
                "password": password,
                "automatic": automatic
            },
            dataType: "json",
            success: function (data, status) {
                $("#wc").text(data);
                if (data.state <= 0) {
                    document.getElementsByClassName('reg_hed_right')[0].children[1].innerHTML = data.information;
                } else {
                    alert("登录成功了");
                }
            }
        });
    });

    // 用户退出登录
    $("#registerUser").on("click", function () {
        $.ajax({
            type: "get",
            url: "signOutLogin",
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 用户信息篇---------------------------
    // 修改用户名
    $("#registerUser").on("click", function () {
        // 用户名
        var userName = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "changeUserName",
            data: {
                "userName": userName
            },
            dataType: "json",
            success: function (data, status) {
                //返回state
            }
        });
    });

    // 修改头像
    $("#registerUser").on("click", function () {
        $.ajax({
            type: "get",
            url: "setUpHeadPortrait",
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 开通关闭个人空间
    $("#registerUser").on("click", function () {
        $.ajax({
            type: "get",
            url: "privacy",
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 用户的关注访问举报篇-----------------------------------
    //查找指定用户关注的所有用户，或被关注所有用户，被访问的记录
    $("#registerUser").on("click", function () {
        // 1表示关注的用户，2表示被关注用户，3表示被访问的记录
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "showFollowUser",
            data: {
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回List<User>
            }
        });
    });

    //点击关注指定用户或访问指定用户的空间的时候
    $("#registerUser").on("click", function () {
        // 1被关注者或被访问者的id
        var id = $("#registerUserName").val();
        // 1表示关注，2表示访问
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "followUser",
            data: {
                "id": id,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回state
            }
        });
    });

    //点击取消关注指定用户时候执行该方法
    $("#registerUser").on("click", function () {
        // 1被关注者或被访问者的id
        var id = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "cancelFollowUser",
            data: {
                "id": id
            },
            dataType: "json",
            success: function (data, status) {
                //返回state
            }
        });
    });

    // 举报用户
    $("#registerUser").on("click", function () {
        // 被举报者的邮箱
        var mailbox = $("#registerUserName").val();
        // 发送的内容
        var content = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "reportUser",
            data: {
                "mailbox": mailbox,
                "content": content
            },
            dataType: "json",
            success: function (data, status) {
                //返回state
            }
        });
    });

    // 邮箱篇-------------------------------------------
    //用户之间发送邮件执行次方法
    $("#registerUser").on("click", function () {
        // 接收者的邮箱
        var mailbox = $("#registerUserName").val();
        // 发送的内容
        var content = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "sendMailUser",
            data: {
                "mailbox": mailbox,
                "content": content
            },
            dataType: "json",
            success: function (data, status) {
                //返回state
            }
        });
    });

    //给客服发邮件
    $("#registerUser").on("click", function () {
        // 发送的内容
        var content = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "feedback",
            data: {
                "content": content
            },
            dataType: "json",
            success: function (data, status) {
                //返回state
            }
        });
    });



};