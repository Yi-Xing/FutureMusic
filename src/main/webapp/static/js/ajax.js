// 后台管理系统：
window.onload = function () {
    // 用户篇----------------------------
    // 显示用户信息，根据条件页数显示信息
    function showUserInformation(page) {
        //得到4个条件存成数组
        var condition = $("#registerUserName").val();
        var userDate = $("#registerUserName").val();
        var userBalance = $("#registerUserName").val();
        var userReport = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showUser",
            data: {
                "condition": condition,
                "pageNum": page
            },
            dataType: "json",
            success: function (data, status) {
                // 返回用户信息
            }
        });
    }

    // 修改用户信息 等级  VIP时间  余额  举报次数
    $("#registerUser").on("click", function () {
        // 得到用户id 等级  VIP时间  余额  举报次数
        var userId = $("#registerUserName").val();
        var userLevel = $("#registerUserName").val();
        var userDate = $("#registerUserName").val();
        var userBalance = $("#registerUserName").val();
        var userReport = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/modifyUser",
            data: {
                "id": userId,
                "level": userLevel,
                "vipDate": userDate,
                "balance": userBalance,
                "report": userReport
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 显示粉丝量
    $("#registerUser").on("click", function () {
        // 得到用户id
        var userId = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "administrators/showFocus?id=" + userId,
            success: function (data, status) {
                // 返回该用户的粉丝量
            }
        });
    });

    //MV篇-------------------------------
    // 添加MV
    $("#registerUser").on("click", function () {
        // 名字
        var name = $("#registerUserName").val();
        // 介绍
        var introduction = $("#registerUserName").val();
        // 等级
        var level = $("#registerUserName").val();
        // 价格
        var price = $("#registerUserName").val();
        // 对应的音乐id
        var musicId = $("#registerUserName").val();
        // 歌手的id
        var singerId = $("#registerUserName").val();
        // 分类的id
        var classificationId = $("#registerUserName").val();
        // 活动的id
        var activity = $("#registerUserName").val();
        // 是否可听
        var available = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/addMusicVideo",
            data: {
                "name": name,
                "introduction": introduction,
                "level": level,
                "price": price,
                "musicId": musicId,
                "singerId": singerId,
                "classificationId": classificationId,
                "activity": activity,
                "available": available
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 显示MV信息，根据条件页数显示信息
    function showUserInformation(page) {
        //得到4个条件存成数组
        var condition = new Array();
        condition[0] = $("#registerUserName").val();
        condition[1] = $("#registerUserName").val();
        condition[2] = $("#registerUserName").val();
        condition[3] = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showMusicVideo",
            data: {
                "condition": condition,
                "pageNum": page
            },
            dataType: "json",
            success: function (data, status) {
                // 返回用户信息
            }
        });
    }

    // 修改MV
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        // 名字
        var name = $("#registerUserName").val();
        // 介绍
        var introduction = $("#registerUserName").val();
        // 等级
        var level = $("#registerUserName").val();
        // 价格
        var price = $("#registerUserName").val();
        // 对应的音乐id
        var musicId = $("#registerUserName").val();
        // 歌手的id
        var singerId = $("#registerUserName").val();
        // 分类的id
        var classificationId = $("#registerUserName").val();
        // 活动的id
        var activity = $("#registerUserName").val();
        // 是否可听
        var available = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/modifyMusicVideo",
            data: {
                "id": id,
                "name": name,
                "introduction": introduction,
                "level": level,
                "price": price,
                "musicId": musicId,
                "singerId": singerId,
                "classificationId": classificationId,
                "activity": activity,
                "available": available
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 音乐或MV综合篇-------------------------------
    // 音乐或MV被收藏的次数
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        // type 1表示是音乐收藏  2表示是MV的收藏
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showMusicCollect",
            data: {
                "id": id,
                "type": type
            },
            success: function (data, status) {
                // 返回int类型
            }
        });
    });

    //  指定音乐或MV的播放量 指定专辑中的所有音乐被播放的次数
    $("#registerUser").on("click", function () {
        // id 音乐或MV或专辑的id
        var id = $("#registerUserName").val();
        // type 1、音乐  2、MV  3、专辑
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showPlay",
            data: {
                "id": id,
                "type": type
            },
            success: function (data, status) {
                // 返回int类型
            }
        });
    });

    // 音乐篇-------------------------------
    // 添加音乐
    $("#registerUser").on("click", function () {
        // 名字
        var name = $("#registerUserName").val();
        // 等级
        var level = $("#registerUserName").val();
        // 价格
        var price = $("#registerUserName").val();
        // 歌手的id
        var singerId = $("#registerUserName").val();
        // 专辑的id
        var albumId = $("#registerUserName").val();
        // 分类的id
        var classificationId = $("#registerUserName").val();
        // 对应的MV id
        var musicVideoId = $("#registerUserName").val();
        // 活动的id
        var activity = $("#22").val();
        // 是否可听
        var available = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/addMusic",
            data: {
                "name": name,
                "level": level,
                "price": price,
                "singerId": singerId,
                "albumId": albumId,
                "classificationId": classificationId,
                "musicVideoId": musicVideoId,
                "activity": activity,
                "available": available
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 显示音乐信息，根据条件页数显示信息
    function showUserInformation(page) {
        //得到4个条件存成数组
        var condition = new Array();
        condition[0] = $("#registerUserName").val();
        condition[1] = $("#registerUserName").val();
        condition[2] = $("#registerUserName").val();
        condition[3] = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showMusic",
            data: {
                "condition": condition,
                "pageNum": page
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    }

    // 修改音乐
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        // 名字
        var name = $("#registerUserName").val();
        // 等级
        var level = $("#registerUserName").val();
        // 价格
        var price = $("#registerUserName").val();
        // 歌手的id
        var singerId = $("#registerUserName").val();
        // 专辑的id
        var albumId = $("#registerUserName").val();
        // 分类的id
        var classificationId = $("#registerUserName").val();
        // 对应的MV id
        var musicVideoId = $("#registerUserName").val();
        // 活动的id
        var activity = $("#registerUserName").val();
        // 是否可听
        var available = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/modifyMusic",
            data: {
                "id": id,
                "name": name,
                "level": level,
                "price": price,
                "singerId": singerId,
                "albumId": albumId,
                "classificationId": classificationId,
                "musicVideoId": musicVideoId,
                "activity": activity,
                "available": available
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 分类篇----------------------------------
    //添加分类
    $("#registerUser").on("click", function () {
        // type 1、languages  2、region 3、gender 4、type
        var classification = $("#registerUserName").val();
        // 值
        var value = $("#registerUserName").val();
        var languages = null;
        var region = null;
        var gender = null;
        var type = null;
        if (classification === 0) {
            languages = value;
        } else if (classification === 1) {
            region = value;
        } else if (classification === 2) {
            gender = value;
        } else if (classification === 3) {
            type = value;
        }
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/addClassification",
            data: {
                "languages": languages,
                "region": region,
                "gender": gender,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    //删除分类
    $("#registerUser").on("click", function () {
        // type 1、languages  2、region 3、gender 4、type
        var classification = $("#registerUserName").val();
        // 值
        var value = $("#registerUserName").val();
        var languages;
        var region;
        var gender;
        var type;
        if (classification === 0) {
            languages = value;
        } else if (classification === 1) {
            region = value;
        } else if (classification === 2) {
            gender = value;
        } else if (classification === 3) {
            type = value;
        }
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/deleteClassification",
            data: {
                "languages": languages,
                "region": region,
                "gender": gender,
                "type": type
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 歌单专辑篇-------------------------------
    // 显示指的的歌单或专辑的详细信息
    $(".songListInformation").on("click", function () {
        //得到4个条件存成数组
        var id = this.value;
        alert(id);
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "/administrators/showIdSongList",
            data: {
                "id": id
            },
            dataType: "json",
            success: function (data, status) {
                // 返回showSongList
                document.getElementById("songListIntroduce").innerHTML=data.introduction;
                document.getElementById("songListPicture").src=data.picture;
            }
        });
    });

    // 修改专辑信息（仅修改活动）
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        // 活动的id
        var activity = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/modifySongList",
            data: {
                "id": id,
                "activity": activity
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 删除指定id的专辑或歌单
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "administrators/deleteSongList?id=" + id,
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 指定歌单或专辑被收藏的次数
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        // type
        var type = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showSongListCollect",
            data: {
                "id": id,
                "type": type
            },
            success: function (data, status) {
                // 返回int
            }
        });
    });

    // 查找指定专辑或歌单中的所有音乐
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        // type
        var type = $("#registerUserName").val();
        // pageNum
        var pageNum = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showMusicSongList",
            data: {
                "id": id,
                "type": type,
                "pageNum": pageNum
            },
            dataType: "json",
            success: function (data, status) {
                // 返回PageInfo
            }
        });
    });

    // 活动篇-----------------------------------
    //显示活动信息
    function showUserInformation(page) {
        //得到4个条件存成数组
        var condition = new Array();
        condition[0] = $("#registerUserName").val();
        condition[1] = $("#registerUserName").val();
        condition[2] = $("#registerUserName").val();
        condition[3] = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showActivity",
            data: {
                "condition": condition,
                "pageNum": page
            },
            dataType: "json",
            success: function (data, status) {
                // 返回PageInfo
            }
        });
    }

    // 添加活动信息
    $("#registerUser").on("click", function () {
        // 名字
        var name = $("#registerUserName").val();
        // 折扣百分比
        var discount = $("#registerUserName").val();
        // 1是折扣是针对音乐，为2的时候是针对购买vip的  3是针对专辑
        var type = $("#registerUserName").val();
        // 活动的详细信息
        var content = $("#registerUserName").val();
        // 折扣百分比
        var website = $("#registerUserName").val();
        // 开始时间
        var startDate = $("#registerUserName").val();
        // 结束时间
        var endDate = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/addActivity",
            data: {
                "name": name,
                "discount": discount,
                "type": type,
                "content": content,
                "website": website,
                "startDate": startDate,
                "endDate": endDate
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 修改活动信息
    $("#registerUser").on("click", function () {
        // 名字
        var name = $("#registerUserName").val();
        // 折扣百分比
        var discount = $("#registerUserName").val();
        // 1是折扣是针对音乐，为2的时候是针对购买vip的  3是针对专辑
        var type = $("#registerUserName").val();
        // 活动的详细信息
        var content = $("#registerUserName").val();
        // 折扣百分比
        var website = $("#registerUserName").val();
        // 开始时间
        var startDate = $("#registerUserName").val();
        // 结束时间
        var endDate = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/modifyActivity",
            data: {
                "name": name,
                "discount": discount,
                "type": type,
                "content": content,
                "website": website,
                "startDate": startDate,
                "endDate": endDate
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 删除活动信息
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "administrators/deleteActivity?id=" + id,
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 评论篇——--------------------------------
    //显示评论
    function showUserInformation(page) {
        //得到4个条件存成数组
        var condition = new Array();
        condition[0] = $("#registerUserName").val();
        condition[1] = $("#registerUserName").val();
        condition[2] = $("#registerUserName").val();
        condition[3] = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showComment",
            data: {
                "condition": condition,
                "pageNum": page
            },
            dataType: "json",
            success: function (data, status) {
                // 返回PageInfo
            }
        });
    }

    // 删除评论
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "administrators/deleteComment?id=" + id,
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 邮箱篇-------------------------------------
    //显示邮箱
    function showUserInformation(page) {
        //得到4个条件存成数组
        var condition = new Array();
        condition[0] = $("#registerUserName").val();
        condition[1] = $("#registerUserName").val();
        condition[2] = $("#registerUserName").val();
        condition[3] = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showMail",
            data: {
                "condition": condition,
                "pageNum": page
            },
            dataType: "json",
            success: function (data, status) {
                // 返回PageInfo
            }
        });
    }

    // 添加邮箱
    $("#registerUser").on("click", function () {
        // 发送方的id
        var senderId = $("#registerUserName").val();
        // 接收方的id
        var recipientId = $("#registerUserName").val();
        // 发送的信息
        var content = $("#registerUserName").val();
        // 发送的时间
        var date = $("#registerUserName").val();
        // 0为普通邮件，1为管理员和客服看的邮件（为1时不用填接收方的id）
        var reply = $("#registerUserName").val();
        // 开始时间
        var startDate = $("#registerUserName").val();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "post",
            url: "administrators/addMail",
            data: {
                "senderId": senderId,
                "recipientId": recipientId,
                "content": content,
                "date": date,
                "startDate": startDate,
                "reply": reply
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
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
            url: "administrators/modifyMail",
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

    // 删除邮箱
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "administrators/deleteMail?id=" + id,
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });

    // 订单篇----------------------------------------
    //显示订单
    function showUserInformation(page) {
        //得到4个条件存成数组
        var condition = new Array();
        condition[0] = $("#registerUserName").val();
        condition[1] = $("#registerUserName").val();
        condition[2] = $("#registerUserName").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "administrators/showOrder",
            data: {
                "condition": condition,
                "pageNum": page
            },
            dataType: "json",
            success: function (data, status) {
                // 返回PageInfo
            }
        });
    }

    // 删除订单
    $("#registerUser").on("click", function () {
        // id
        var id = $("#registerUserName").val();
        $.ajax({
            type: "get",
            url: "administrators/deleteOrder?id=" + id,
            dataType: "json",
            success: function (data, status) {
                // 返回state
            }
        });
    });


};