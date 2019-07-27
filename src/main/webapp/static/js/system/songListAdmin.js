//后台管理系统的订单管理
window.onload = function () {
    //将菜单变色
    $("#songListPage").css("background-color", "RGB(0,153,102)");

    // 点击编辑按钮触发的事件
    $(".edit").on("click", function () {
        // 得到id
        var id = $(this).data("id");
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/administrators/showIdSongList?id=" + id,
            success: function (data, status) {
                $("#activity").val(data.activity);
                $("#editId").val(id);
            }
        });
    });

    // 点击编辑框的保存按钮
    $("#determineEdit").on("click", function () {
        var id=$("#editId").val();
        var activity=$("#activity").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/modifySongList",
            data: {
                "id": id,
                "activity": activity
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+id+"专辑信息修改成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            },
            error:function () {
                alert("错误")
            }
        });
    });

    // 点击更多按钮触发的事件
    $(".more").on("click", function () {
        var id = $(this).data("id");
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/administrators/showIdSongList?id=" + id,
            success: function (data, status) {
                $('#id').text(id);
                $('#play').text(data.userId);
                $('.mailContent').text(data.introduction);
            }
        });
    });


    //点击删除按钮触发的事件
    $(".delete").on("click", function () {
        // 得到订单id
        var id = $(this).data("id");
        $("#determineDelete").data("id", id);
        $("#deleteId").text(id);
    });

    // 点击确定删除触发
    $("#determineDelete").on("click", function () {
        var id = $(this).data("id");
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/deleteSongList",
            data: {
                "id": id
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+id+"的歌单/专辑信息删除成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });

};