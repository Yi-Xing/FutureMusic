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
                if(data.state===0){
                    alert(data.information);
                }else {
                    alert("用户名:"+userName+"修改成功");
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
            data:selectFile,
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    alert(data.information);
                }else {
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
                if(data.state===0){
                    alert("关注失败");
                }else {
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
                if(data.state===0){
                    alert("取消关注失败");
                }else {
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
                data:selectFile,
                dataType: "json",
                success: function (data, status) {
                    // 返回state
                    if(data.state===0){
                        $(".promptInformation").text(data.information);
                    }else {
                        alert("歌单/专辑创建成功");
                        // 修改成功刷新网页
                        location.reload();
                    }
                }
            });
    });


    // 点击音乐的右边的。。。中的添加到天厨模态框显示，显示用户所有的歌单
    $(".showSongList").on("click", function () {
        $.ajax({
            type: "get",
            url: "/user/getSongList",
            dataType: "json",
            success: function (data, status) {
                //返回state
                for(var i in data){
                    // 得到每个歌单的名字和ID
                    var id=data.id;
                    var name=data.name;
                    // 循环编辑加上单选按钮
                }

            }
        });
    });

    // 选中指定歌单后点击确定提交
    $(".").on("click", function () {
        // 的到用户选中的单选按钮中的歌单Id
        var id=$("").val();
        // 以及音乐的ID
        var musicId=$("").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "/user/SongListAddMusic",
            data: {
                "musicId": musicId,
                "type": 1,
                "belongId": id
            },
            dataType: "json",
            success: function (data, status) {
                //返回state
                if(data.state===0){
                    alert("该音乐已在该歌单中");
                }else {
                    alert("添加成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });

    // 在歌单详细信息中点击... 删除将指定音乐从歌单中删除
    $(".").on("click", function () {
        // 的到用户选中的单选按钮中的歌单Id
        var id=$("").val();
        // 以及音乐的ID
        var musicId=$("").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "/user/SongListDeleteMusic",
            data: {
                "musicId": musicId,
                "type": 1,
                "belongId": id
            },
            dataType: "json",
            success: function (data, status) {
                //返回state
                if(data.state===0){
                    alert("请重试");
                }else {
                    alert("添加成功");
                    // 修改成功刷新网页
                    location.reload();
                }
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