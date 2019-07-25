//后台管理系统的用户管理
window.onload = function () {
    //将菜单变色
    $("#userPage").css("background-color", "RGB(0,153,102)");

    // 点击编辑按钮触发的事件
    $(".editUser").on("click", function () {
        // 得到用户id
        var userId = $(this).data("id");
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/administrators/showUserInformatics?id=" + userId,
            success: function (data, status) {
                // User 返回该用户所有信息，用户的id存储用户的粉丝量
                $("#editUserId").val(userId);
                $('#vip').val(data.level);
                $('#balance').val(data.balance);
                $('#report').val(data.report);
            }
        });
    });

    // 点击编辑框的保存按钮
    $("#determineEditUser").on("click", function () {
        // 得到用户id 等级   余额  举报次数
        var userId = $("#editUserId").val();
        var userLevel = $("#vip").val();
        var userBalance = $("#balance").val();
        var userReport = $("#report").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/modifyUser",
            data: {
                "id": userId,
                "level": userLevel,
                "balance": userBalance,
                "report": userReport
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+userId+"用户信息修改成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });

    // 点击更多按钮触发的事件
    $(".moreUser").on("click", function () {
        // 得到用户id
        var userId = $(this).data("id");
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/administrators/showUserInformatics?id=" + userId,
            success: function (data, status) {
                // User 返回该用户所有信息，用户的id存储用户的粉丝量
                $('#moreEditUserId').val(userId);
                $('#userId').text(userId);
                $('#fans').text(data.id);
                $('#creationNumber').attr("placeholder", data.date);
                $('#vipTime').val(data.vipDate);
<<<<<<< HEAD
=======
                $("#order").attr("href","/administrators/showOrder?condition=&condition=&condition="+userId+"&condition=");
                $("#album").attr("href","/administrators/showSongList?condition=2&condition=3&condition="+userId);
                $("#music").attr("href","/administrators/showMusic?condition=&condition=&condition=3&condition="+userId);
                $("#comment").attr("href","/administrators/showComment?condition=&condition="+userId+"&condition=7&condition=");
>>>>>>> 0ca05eefec8f2220ae7247ab5c20916769c5f895
            }
        });
    });

    // 点击更多框的保存按钮
    $("#moreEditUser").on("click", function () {
        // 得到用户id vip时间
        var userId = $("#moreEditUserId").val();
        var vipDate = $("#vipTime").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/modifyUserVipDate",
            data: {
                "id": userId,
                "vipDate": vipDate
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+userId+"用户信息修改成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });


};