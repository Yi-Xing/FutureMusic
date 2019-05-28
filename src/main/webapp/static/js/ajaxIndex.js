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
            url: "showSendMail?id="+id,
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
            url: "showReceiveMail?id="+id,
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
    $("#registerUser").on("click", function () {
        // 1表示查找音乐收藏 2表示查找MV收藏
        var type = $("#registerUserName").val();
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
            url: "showMusicPlay?type="+type,
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
            url: "playMusic?id="+id,
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
            url: "playMusicVideo?id="+id,
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
            url: "deleteComment?id="+id,
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
            url: "commentFabulous?id="+id,
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
            url: "showUserSongList?type="+type,
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
            url: "showUserCollectionSongList?type="+type,
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
            url: "editMusicSongListPicture?id="+id,
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
            url: "deleteMusicSongList?id="+id,
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

};