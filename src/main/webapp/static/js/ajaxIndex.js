// 首页登录注册和用户页面：
window.onload = function () {
    //登录注册篇---------------------------
    // 用于发送验证码
    var pp = $(".reg_hed_right")[0];
    $("#button4").on("click", function () {
        var registerMail = $("#registerMail").val();
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
            }
        });
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
    $("#login").on("click", function () {
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
                    window.location.href ="http://localhost:8080/administrators/showUser";
                }
            }
        });
    });

    // 用户退出登录
    $("#signOutLogin").on("click", function () {
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

    // 查看给那些用户发送过邮件
    $("#registerUser").on("click", function () {
        $.ajax({
            type: "get",
            url: "showSendMailUser",
            dataType: "json",
            success: function (data, status) {
                //返回List<User>
            }
        });
    });

    // 查看给指定用户发过那些邮件
    $("#registerUser").on("click", function () {
        // 用户的id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "showSendMail?id=" + id,
            dataType: "json",
            success: function (data, status) {
                //返回List<Mail>
            }
        });
    });

    // 查看收到过那些用户发送过邮件
    $("#registerUser").on("click", function () {
        $.ajax({
            type: "get",
            url: "showReceiveMailUser",
            dataType: "json",
            success: function (data, status) {
                //返回List<User>
            }
        });
    });

    // 查看给指定用户发过那些邮件
    $("#registerUser").on("click", function () {
        // 用户的id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "showReceiveMail?id=" + id,
            dataType: "json",
            success: function (data, status) {
                //返回List<Mail>
            }
        });
    });

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
    //显示用户收藏的所有音乐，显示用户收藏的所有MV
        // 1表示查找音乐收藏 2表示查找MV收藏
    $(".MyFavoriteMusic").on("click", function () {
        var type = $(this).data("value");
        // alert(type);
        console.log(type);
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "showUserCollectionMusic",
            data: {
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回 List<MusicCollect>

            }
        });
    });

    //显示用户购买过的音乐，显示用户购买过的MV
    $("#registerUser").on("click", function () {
        // 1表示查找音乐购买 2表示查找MV购买
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "showUserPurchaseMusic",
            data: {
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回 List<Music>或List<MusicVideo>
            }
        });
    });

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

    // 显示音乐或MV的播放记录
    $("#registerUser").on("click", function () {
        // 1是音乐2是MV
        var type = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "showMusicPlay?type=" + type,
            dataType: "json",
            success: function (data, status) {
                //返回 List<Music>或List<MusicVideo>
            }
        });
    });

    // 播放音乐
    $("#registerUser").on("click", function () {
        // 音乐的id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "playMusic?id=" + id,
            dataType: "json",
            success: function (data, status) {
                //返回 Music
            }
        });
    });

    // 播放MV
    $("#registerUser").on("click", function () {
        // MV的id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "playMusicVideo?id=" + id,
            dataType: "MusicVideo",
            success: function (data, status) {
                //返回 Music
            }
        });
    });

    //今后还有下载音乐，下载MV


    //评论篇-----------------------------------------
    // 添加音乐或MV或专辑的评论
    $("#registerUser").on("click", function () {
        // 音乐或MV或专辑的id
        var musicId = $("#registerUserName").val();
        // 1是音乐，2是MV，3是专辑
        var type = $("#registerUserName").val();
        // 评论的内容
        var content = $("#registerUserName").val();
        // 回复哪个评论的id, 0为独立评论
        var reply = $("#registerUserName").val();

        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "comment",
            data: {
                "musicId": musicId,
                "type": type,
                "content": content,
                "reply": reply
            },
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });

    // 删除指定id的评论
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "deleteComment?id=" + id,
            dataType: "json",
            success: function (data, status) {
                //返回State
            }
        });
    });

    // 评论点赞或取消点赞
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "commentFabulous?id=" + id,
            dataType: "json",
            success: function (data, status) {
                //返回State
            }
        });
    });

    // 歌单专辑篇----------------------------------------

    //显示用户创建的专辑或歌单
    $("#registerUser").on("click", function () {
        // 1是歌单2是专辑
        var type = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "showUserSongList?type=" + type,
            dataType: "json",
            success: function (data, status) {
                //返回 List<SongList>
            }
        });
    });

    // 显示用户收藏的所有歌单或专辑
    $("#registerUser").on("click", function () {
        // 1是歌单2是专辑
        var type = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "showUserCollectionSongList?type=" + type,
            dataType: "json",
            success: function (data, status) {
                //返回 List<SongListCollect>
            }
        });
    });

    // 创建歌单或专辑
    $("#registerUser").on("click", function () {
        // 歌单或专辑的标题
        var name = $("#registerUserName").val();
        // 歌单或专辑的介绍
        var introduction = $("#registerUserName").val();
        // 分类
        var classification = $("#registerUserName").val();
        // 获取类型1是歌单2是专辑
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "editMusicSongList",
            data: {
                "name": name,
                "introduction": introduction,
                "classification": classification,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });

    // 编辑歌单或专辑的基本信息
    $("#registerUser").on("click", function () {
        // 歌单或专辑的标题
        var name = $("#registerUserName").val();
        // 歌单或专辑的介绍
        var introduction = $("#registerUserName").val();
        // 分类
        var classification = $("#registerUserName").val();
        // 获取类型1是歌单2是专辑
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "editMusicSongList",
            data: {
                "name": name,
                "introduction": introduction,
                "classification": classification,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });

    // 编辑歌单或专辑的封面图片
    $("#registerUser").on("click", function () {
        // 主键 id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "editMusicSongListPicture?id=" + id,
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });

    // 编辑指定歌单或专辑
    $("#registerUser").on("click", function () {
        // 主键 id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "deleteMusicSongList?id=" + id,
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });

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

    // 充值VIP
    $("#registerUser").on("click", function () {
        // 指定的充值几个月
        var count = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "rechargeVIP",
            data: {
                "count": count,
            },
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });

    //安全中心页面的功能--------------------------------
    //绑定邮箱
    $("#registerUser").on("click", function () {
        // 邮箱账号
        var mailbox = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "bindingMailbox",
            data: {
                "mailbox": mailbox,
            },
            dataType: "json",
            success: function (data, status) {
                //返回 State
            }
        });
    });

    // 修改密码
    $("#registerUser").on("click", function () {
        //原密码
        var originalPassword = $("#registerUserName").val();
        // 现密码
        var password = $("#registerMail").val();
        // 现密码2
        var passwordAgain = $("#registerPassword").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "register",
            data: {
                "originalPassword": originalPassword,
                "password": password,
                "passwordAgain": passwordAgain
            },
            dataType: "json",
            success: function (data, status) {
            }
        });
    });

    // 设置密保或修改密保
    $("#registerUser").on("click", function () {
        //用户输入的验证码
        var verificationCode = $("#registerUserName").val();
        // 密保——性别
        var gender = $("#registerMail").val();
        // 密保——年龄
        var age = $("#registerPassword").val();
        //  密保——出生日期
        var birthday = $("#registerPassword").val();
        // 密保——住址
        var address = $("#registerPassword").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "addSecretProtection",
            data: {
                "verificationCode": verificationCode,
                "gender": gender,
                "age": age,
                "birthday": birthday,
                "address": address
            },
            dataType: "json",
            success: function (data, status) {
            }
        });
    });

    // 用于发送验证码
    $("#button24").on("click", function () {
        $.ajax({
            type: "get",
            url: "secretProtectionVerificationCode",
            dataType: "json",
            success: function (data) {
            }
        });
    });

    // 找回密码第一步
    $("#button42").on("click", function () {
        $.ajax({
            type: "get",
            url: "verificationAccount2",
            dataType: "json",
            success: function (data) {
            }
        });
    });

    // 找回密码第二步
    $("#registerUser").on("click", function () {
        //用户输入的验证码
        var verificationCode = $("#registerUserName").val();
        // 密保——性别
        var gender = $("#registerMail").val();
        // 密保——年龄
        var age = $("#registerPassword").val();
        //  密保——出生日期
        var birthday = $("#registerPassword").val();
        // 密保——住址
        var address = $("#registerPassword").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "addSecretProtection",
            data: {
                "verificationCode": verificationCode,
                "gender": gender,
                "age": age,
                "birthday": birthday,
                "address": address
            },
            dataType: "json",
            success: function (data, status) {
            }
        });
    });
};