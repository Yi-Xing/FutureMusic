//后台管理系统的订单管理
window.onload = function () {
    //将菜单变色
    $("#musicPage").css("background-color", "RGB(0,153,102)");

    // 点击编辑按钮触发的事件
    $(".edit").on("click", function () {
        // 得到音乐id
        var id = $(this).data("id");
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/administrators/showIdMusic?id=" + id,
            success: function (data, status) {
                // User 返回该用户所有信息，用户的id存储用户的粉丝量
                $("#editId").val(id);
                $("#name").val(data.name);
                $("#singerId").val(data.singerId);
                $("#albumId").val(data.albumId);
                $("#classificationId").val(data.classificationId);
                $("#level").val(data.level);
                $("#price").val(data.price);
                $("#activity").val(data.activity);
                $("#available").val(data.available);
            }
        });
    });

    // 点击编辑框的保存按钮
    $("#determineEdit").on("click", function () {
        var id=$("#editId").val();
        alert(id);
        var name=$("#name").val();
        var singerId=$("#singerId").val();
        var albumId=$("#albumId").val();
        var classificationId=$("#classificationId").val();
        var level=$("#level").val();
        var price=$("#price").val();
        var activity=$("#activity").val();
        var available=$("#available").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/modifyEditMusic",
            data: {
                "id": id,
                "name": name,
                "singerId": singerId,
                "albumId": albumId,
                "classificationId": classificationId,
                "level": level,
                "price": price,
                "activity": activity,
                "available": available
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+id+"音乐信息修改成功");
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
            url: "/administrators/showIdMusic?id=" + id,
            success: function (data, status) {
                // User 返回该用户所有信息，用户的id存储用户的粉丝量
                $('#moreEditId').val(id);
                $('#id').text(id);
                $('#play').text(data.playCount);
                $('#uploadDate').attr("placeholder", data.date);
                $('#mvId').val(data.musicVideoId);
                $("#picture").data("value", data.picture);
                $("#path").data("value", data.path);
                $("#lyricPath").data("value", data.lyricPath);
            }
        });
    });

    // 点击更多框的保存按钮
    $("#moreEditUser").on("click", function () {
        // 得到音乐id MVid
        var id = $("#moreEditId").val();
        var musicVideoId = $("#mvId").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/modifyMoreMusic",
            data: {
                "id": id,
                "musicVideoId": musicVideoId
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+userId+"音乐信息修改成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });
};