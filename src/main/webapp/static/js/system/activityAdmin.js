//后台管理系统的活动管理
window.onload = function () {
    //将菜单变色
    $("#activityPage").css("background-color", "RGB(0,153,102)");

    // 点击编辑按钮触发的事件
    $(".edit").on("click", function () {
        // 得到音乐id
        var id = $(this).data("id");
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/administrators/showIdActivity?id=" + id,
            success: function (data, status) {
                $("#editId").val(id);
                $("#name").val(data.name);
                $("#singerId").val(data.type);
                $("#albumId").val(data.discount);
                $("#classificationId").val(data.website);
                $("#level").val(data.startDate);
                $("#price").val(data.endDate);
            }
        });
    });

    // 点击编辑框的保存按钮
    $("#determineEdit").on("click", function () {
        var id=$("#editId").val();
        var name=$("#name").val();
        var type=$("#singerId").val();
        var discount=$("#albumId").val();
        var website=$("#classificationId").val();
        var startDate=$("#level").val();
        var endDate=$("#price").val();

        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/modifyEditActivity",
            data: {
                "id": id,
                "name": name,
                "type": type,
                "discount": discount,
                "website": website,
                "startDate": startDate,
                "endDate": endDate
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+id+"活动信息修改成功");
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
            url: "/administrators/showIdActivity?id=" + id,
            success: function (data, status) {
                // User 返回该用户所有信息，用户的id存储用户的粉丝量
                $('#moreEditId').val(id);
                $('#id').text(id);
                $('#uploadDate').val(data.content);
            }
        });
    });

    // 点击更多框的保存按钮
    $("#preservationMoreEdit").on("click", function () {
        // 得到音乐id MVid
        var id = $("#moreEditId").val();
        var content = $("#uploadDate").val();
        var selectFile = new FormData($('#selectFile')[0]);
        selectFile.append('id', id);
        selectFile.append("content",content);
        selectFile.append("checkbox",$("#musicPicture").is(':checked'));
        $.ajax({
            // contentType: "multipart/form-data",
            type: "post",
            cache: false,
            processData: false,// 使数据不做处理
            contentType: false,// 不要设置Content-Type请求头
            url: "/administrators/modifyMoreActivity",
            data:selectFile,
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
            }
        });
    });

    // 点击添加音乐的保存按钮
    $("#addMusicInformation").on("click", function () {
        // 得到音乐所以信息
        var name=$("#nameAdd").val();
        var type=$("#singerIdAdd").val();
        var discount=$("#albumIdAdd").val();
        var website=$("#classificationIdAdd").val();
        var startDate=$("#levelAdd").val();
        var endDate=$("#priceAdd").val();
        var content=$("#activityAdd").val();
        // 封装文件
        var selectFile = new FormData($('#selectFileAdd')[0]);
        selectFile.append("name",name);
        selectFile.append("type",type);
        selectFile.append("discount",discount);
        selectFile.append("website",website);
        selectFile.append("startDate",startDate);
        selectFile.append("endDate",endDate);
        selectFile.append("content",content);
        $.ajax({
            // contentType: "multipart/form-data",
            type: "post",
            cache: false,
            processData: false,// 使数据不做处理
            contentType: false,// 不要设置Content-Type请求头
            url: "/administrators/addActivity",
            data:selectFile,
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+id+"音乐信息添加成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });
};