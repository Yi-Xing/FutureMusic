// 用户页面：
window.onload = function () {
    // 用户信息篇---------------------------
    // 修改用户名
    $("#modifyUserName").on("click", function () {
        // 用户名
        var userName = $("#exampleInputEmail1").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "/user/changeUserName",
            data: {
                "userName": userName
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if (data.state === 0) {
                    alert(data.information);
                } else {
                    alert("用户名:" + userName + "修改成功");
                    // 修改成功刷新网页
                    window.location.href = '/user/userPage';
                }
            }
        });
    });
    // 修改头像
    $("#modifyUserPortrait").on("click", function () {
        var selectFile = new FormData($('#selectFile')[0]);
        $.ajax({
            // contentType: "multipart/form-data",
            type: "post",
            cache: false,
            processData: false,// 使数据不做处理
            contentType: false,// 不要设置Content-Type请求头
            url: "/user/setUpHeadPortrait",
            data: selectFile,
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if (data.state === 0) {
                    alert(data.information);
                } else {
                    alert("用户头像修改成功");
                    // 修改成功刷新网页
                    window.location.href = '/user/userPage';
                }
            }
        });
    });

    //点击关注指定用户或访问指定用户的空间的时候
    $("#focus").on("click", function () {
        // 1被关注者或被访问者的id
        var id = $(this).data("id");
        // 1表示关注，2表示访问
        var type = 1;
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "/user/followUser",
            data: {
                "id": id,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                if (data.state === 0) {
                    alert("关注失败");
                } else {
                    alert("关注成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });

    //点击取消关注指定用户时候执行该方法
    $("#follow").on("click", function () {
        // 1被关注者或被访问者的id
        var id = $(this).data("id");
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "/user/cancelFollowUser",
            data: {
                "id": id
            },
            dataType: "json",
            success: function (data, status) {
                //返回state
                if (data.state === 0) {
                    alert("取消关注失败");
                } else {
                    alert("取消关注成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });

    // 创建歌单或专辑
    $(".addAlbum").on("click", function () {
        // 歌单或专辑的标题
        var name = $("#nameAdd").val();
        // 歌单或专辑的介绍
        var introduction = $("#activityAdd").val();
        // 获取类型1是歌单2是专辑
        var type = $(this).data("value");
        // 得到文件上传对象
        var selectFile = new FormData($('#selectFileAdd')[0]);
        selectFile.append('name', name);
        selectFile.append('introduction', introduction);
        selectFile.append('type', type);
        $.ajax({
            // contentType: "multipart/form-data",
            type: "post",
            cache: false,
            processData: false,// 使数据不做处理
            contentType: false,// 不要设置Content-Type请求头
            url: "/user/createMusicSongList",
            data: selectFile,
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if (data.state === 0) {
                    $(".promptInformation").text(data.information);
                } else {
                    alert("歌单/专辑创建成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });

    // 点击编辑按钮触发的事件
    $(".editSongList").on("click", function () {
        var id=$(this).data("id");
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/user/showIdSongList?id=" + id,
            success: function (data, status) {
                $("#nameEdit").val(data.name);
                $("#activityEdit").val(data.introduction);
                $("#editSongList").data("id",id);
            }
        });
    });

    // 编辑歌单或专辑的封面图片
    $("#editSongList").on("click", function () {
        // 主键 id
        var id=$(this).data("id");
        var name = $("#nameEdit").val();
        var introduction = $("#activityEdit").val();
        var selectFile = new FormData($('#selectFileEdit')[0]);
        selectFile.append('id', id);
        selectFile.append("name",name);
        selectFile.append("introduction",introduction);
        selectFile.append("fileCheckbox",$("#musicPicture").is(':checked'));
            $.ajax({
            // contentType: "multipart/form-data",
            type: "post",
            cache: false,
            processData: false,// 使数据不做处理
            contentType: false,// 不要设置Content-Type请求头
            url: "/user/editMusicSongList",
            data:selectFile,
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("歌单/专辑信息修改成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });






    // 点击删除按钮触发的事件
    $(".deleteSongList").on("click", function () {
        var id=$(this).data("id");
        $("#deleteSongList").data("id",id);
    });
    // 删除指定歌单
    $("#deleteSongList").on("click", function () {
        // 主键 id
        var id = $(this).data("id");
        $.ajax({
            type: "get",
            url: "deleteMusicSongList?id=" + id,
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if (data.state === 0) {
                    $(".promptInformation").text(data.information);
                } else {
                    alert("歌单删除成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });


    // 点击发送邮箱按钮触发的事件
    $("#send").on("click", function () {
        var mailbox=$("#mailbox").val();
        var content=$("#content").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/user/sendMailUser",
            data: {
                "mailbox": mailbox,
                "content": content
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    alert(data.information);
                }else {
                    alert("邮箱发送成功");
                    location.reload();
                }
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
};