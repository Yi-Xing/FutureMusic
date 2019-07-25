//后台管理系统的MV管理
window.onload = function () {
    //将菜单变色
    $("#musicVideoPage").css("background-color", "RGB(0,153,102)");

    // 点击编辑按钮触发的事件
    $(".edit").on("click", function () {
        // 得到MVid
        var id = $(this).data("id");
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/administrators/showIdMusicVideo?id=" + id,
            success: function (data, status) {
                $("#editId").val(id);
                $("#name").val(data.name);
                $("#singerId").val(data.singerId);
                $("#albumId").val(data.musicId);
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
        var name=$("#name").val();
        var singerId=$("#singerId").val();
        var musicId=$("#albumId").val();
        var classificationId=$("#classificationId").val();
        var level=$("#level").val();
        var price=$("#price").val();
        var activity=$("#activity").val();
        var available=$("#available").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/modifyEditMusicVideo",
            data: {
                "id": id,
                "name": name,
                "singerId": singerId,
                "musicId": musicId,
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
                    alert("id:"+id+"MV信息修改成功");
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
            url: "/administrators/showIdMusicVideo?id=" + id,
            success: function (data, status) {
                $('#moreEditId').val(id);
                $('#id').text(id);
                $('#play').text(data.playCount);
                $('#uploadDate').attr("placeholder", data.date);
                $('#mvId').val(data.introduction);


                $("#path").data("value", data.path);
                $("#picture").data("value", data.picture);
            }
        });
    });

    // 点击更多框的保存按钮
    $("#preservationMoreEdit").on("click", function () {
        // 得到音乐id MVid
        var id = $("#moreEditId").val();
        var introduction = $("#mvId").val();
        var selectFile = new FormData($('#selectFile')[0]);
        selectFile.append('id', id);
        selectFile.append("introduction",introduction);
        selectFile.append("fileCheckbox",$("#musicPicture").is(':checked'));
        selectFile.append("fileCheckbox",$("#musicFile").is(':checked'));
        $.ajax({
            // contentType: "multipart/form-data",
            type: "post",
            cache: false,
            processData: false,// 使数据不做处理
            contentType: false,// 不要设置Content-Type请求头
            url: "/administrators/modifyMoreMusicVideo",
            data:selectFile,
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+id+"MV信息修改成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });

    // 点击添加音乐的保存按钮
    $("#addMusicInformation").on("click", function () {
        // 得到音乐所以信息
        var name=$("#nameAdd").val();
        var singerId=$("#singerIdAdd").val();
        var musicId=$("#albumIdAdd").val();
        var classificationId=$("#classificationIdAdd").val();
        var level=$("#levelAdd").val();
        var price=$("#priceAdd").val();
        var activity=$("#activityAdd").val();
        var available=$("#availableAdd").val();
        var introduction = $("#mvIdAdd").val();
        // 封装文件
        var selectFile = new FormData($('#selectFileAdd')[0]);
        selectFile.append("name",name);
        selectFile.append("singerId",singerId);
        selectFile.append("musicId",musicId);
        selectFile.append("classificationId",classificationId);
        selectFile.append("level",level);
        selectFile.append("price",price);
        selectFile.append("activity",activity);
        selectFile.append("available",available);
        selectFile.append("introduction",introduction);
        $.ajax({
            // contentType: "multipart/form-data",
            type: "post",
            cache: false,
            processData: false,// 使数据不做处理
            contentType: false,// 不要设置Content-Type请求头
            url: "/administrators/addMusicVideo",
            data:selectFile,
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+id+"MV信息添加成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });
};