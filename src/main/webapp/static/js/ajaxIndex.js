// 首页登录注册和用户页面：
window.onload = function () {
    //登录注册篇---------------------------
    // 用于发送验证码
    var pp = $(".reg_hed_right")[0];
    var canSend = 0;
    $("#verificationCode").on("click", function () {
        var registerMail = $("#registerMail").val();

        if (canSend === 0) {canSend = 1;
            $.ajax({
                type: "get",
                url: "/registerVerificationCode?mailbox=" + registerMail,
                dataType: "json",
                success: function (data) {
                    if (data.state !== 1) {
                        pp.children[0].innerHTML = "";
                        pp.children[1].innerHTML = data.information;
                        pp.children[2].innerHTML = "";
                        pp.children[3].innerHTML = "";
                    } else {
                        pp.children[0].innerHTML = "";
                        pp.children[1].innerHTML = "发送成功";
                        pp.children[2].innerHTML = "";
                        pp.children[3].innerHTML = "";
                    }
                    $("#verificationCode").attr('disabled', 'true');
                }
            });
        }
        setTimeout(function () {
            canSend = 0;
        }, 30000);
    });


    // 用于注册账号
    $("#registerUserInformation").on("click", function () {
        var userName = $("#registerName").val();
        var sendMail = $("#registerMail").val();
        var password = $("#registerPassword").val();
        var passwordAgain = $("#registerPasswordAgain").val();
        var verificationCode = $("#registerVerificationCode").val();
        var agreement = $("#registerAgreement").is(":checked");
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "/register",
            data: {
                "userName": userName,
                "sendMail": sendMail,
                "password": password,
                "passwordAgain": passwordAgain,
                "verificationCode": verificationCode,
                "agreement": agreement
            },
            dataType: "json",
            success: function (data, status) {
                if (data.state === -1) {
                    pp.children[0].innerHTML = data.information;
                    pp.children[1].innerHTML = "";
                    pp.children[2].innerHTML = "";
                    pp.children[3].innerHTML = "";
                } else if (data.state === -2) {
                    pp.children[0].innerHTML = "";
                    pp.children[1].innerHTML = data.information;
                    pp.children[2].innerHTML = "";
                    pp.children[3].innerHTML = "";
                } else if (data.state === -3) {
                    pp.children[0].innerHTML = "";
                    pp.children[1].innerHTML = "";
                    pp.children[2].innerHTML = data.information;
                    pp.children[3].innerHTML = "";
                } else if (data.state === -4) {
                    pp.children[0].innerHTML = "";
                    pp.children[1].innerHTML = "";
                    pp.children[2].innerHTML = "";
                    pp.children[3].innerHTML = data.information;
                } else {
                    // 刷新网页
                    alert("注册成功了");
                        location.reload();
                    // setTimeout(function () {
                    // }, 1000); //指定1秒刷新一次
                }
            }
        });
    });

    // 用于登录账号
    $("#login").on("click", function (){
        // 账号
        var mailbox = $("#loginMail").val();
        // 密码
        var password = $("#loginPassword").val();
        // 是否选中7天登录
        var automatic = $("#automatic").is(":checked");
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "/loginAccount",
            data: {
                "mailbox": mailbox,
                "password": password,
                "automatic": automatic
            },
            dataType: "json",
            success: function (data, status) {
                if (data.state === 0) {
                    $('#loginTips').html(data.information);
                } else if (data.state === 1) {
                    $('#loginTips').html("");
                    // 刷新网页
                    alert("登录成功了");
                    // setTimeout(function () {
                    // }, 1000); //指定1秒刷新一次
                        location.reload();
                }else if(data.state === 2){
                    window.location.href ="http://localhost:8080/administrators/showHomePage";
                }
            }
        });
    });

    // 用户退出登录
     $(".signOutLogin").on("click", function () {
        $.ajax({
            type: "get",
            url: "/signOutLogin",
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===1){
                    location.reload();
                }
            }
        });
    });

    // 邮箱篇-------------------------------------------

    // 修改邮箱（只能修改状态）
    $("#registerUser").on("click", function () {
        //邮箱的id
        var id = $("#registerUserName").val();
        // 0表示未读，1表示已读，2表示标记
        var state = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "modifyMailState",
            data: {
                "id": id,
                "state": state
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    //音乐和MV篇-------------------------------------------

    //收藏或取消收藏音乐或MV
    $("#registerUser").on("click", function () {
        // id 音乐或MV的id
        var musicId = $("#registerUserName").val();
        // 1是音乐2是MV
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "collectionMusic",
            data: {
                "musicId": musicId,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });

    // 添加音乐或MV的历史播放记录
    $("#registerUser").on("click", function () {
        // id 音乐或MV的id
        var musicId = $("#registerUserName").val();
        // 1是音乐2是MV
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "musicPlay",
            data: {
                "musicId": musicId,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });

    // 歌单专辑篇----------------------------------------

    // 收藏或取消收藏歌单或专辑
    $("#registerUser").on("click", function () {
        // 歌单或专辑的id
        var id = $("#registerUserName").val();
        // 1是歌单2是专辑
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "collectionSongList",
            data: {
                "id": id,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回State
            }
        });
    });

    // 将指定音乐添加到专辑或歌单中
    $("#registerUser").on("click", function () {
        // 专辑或歌单的id
        var belongId = $("#registerUserName").val();
        // 1是歌单2是专辑
        var type = $("#registerUserName").val();
        // 获取类型1是歌单2是专辑
        var musicId = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "SongListAddMusic",
            data: {
                "belongId": belongId,
                "musicId": musicId,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });

    // 支付篇-----------------------------------------
    // 点击购买音乐或MV
    $("#registerUser").on("click", function () {
        // 购买的id
        var id = $("#registerUserName").val();
        // 1表示音乐  2表示MV
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "purchase",
            data: {
                "id": id,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });


};