// 后台管理系统：
window.onload = function () {
    // 用户篇----------------------------
    // 显示用户信息，根据条件页数显示信息
    function showUserInformation(page) {
        //得到4个条件存成数组
        var condition = $("#registerUserName").val();
        var userDate = $("#registerUserName").val();
        var userBalance= $("#registerUserName").val();
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
        var userBalance= $("#registerUserName").val();
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
            url: "administrators/showFocus?id="+userId,
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
        var level= $("#registerUserName").val();
        // 价格
        var price=$("#registerUserName").val();
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
        var condition=new Array();
        condition[0] = $("#registerUserName").val();
        condition[1] = $("#registerUserName").val();
        condition[2]= $("#registerUserName").val();
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
        var level= $("#registerUserName").val();
        // 价格
        var price=$("#registerUserName").val();
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
        var level= $("#registerUserName").val();
        // 价格
        var price= $("#registerUserName").val();
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
        var condition=new Array();
        condition[0] = $("#registerUserName").val();
        condition[1] = $("#registerUserName").val();
        condition[2]= $("#registerUserName").val();
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
        var level= $("#registerUserName").val();
        // 价格
        var price= $("#registerUserName").val();
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
        var languages=null;
        var region=null;
        var gender=null;
        var type=null;
        if(classification===0){
            languages=value;
        }else if(classification===1){
            region=value;
        }else if(classification===2){
            gender=value;
        }else if(classification===3){
            type=value;
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
        if(classification===0){
            languages=value;
        }else if(classification===1){
            region=value;
        }else if(classification===2){
            gender=value;
        }else if(classification===3){
            type=value;
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
            // alert(data.state);
            // alert(data.information);
            if (data.state) {
                // alert("我调用了");
            }
            $("#wc").text(data);
            if (data.state == 0) {
                document.getElementsByClassName('reg_hed_right')[0].children[1].innerHTML = data.information;
            } else {
                alert("注册成功了");
            }
        }
    });
}
