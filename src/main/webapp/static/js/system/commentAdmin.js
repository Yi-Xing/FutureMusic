//后台管理系统的评论管理
window.onload = function () {
    //将菜单变色
    $("#commentPage").css("background-color", "RGB(0,153,102)");

    // 点击更多按钮触发的事件
    $(".moreComment").on("click", function () {
        // 得到评论id
        var commentId = $(this).data("id");
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/administrators/showIdComment?id=" + commentId,
            success: function (data, status) {
                // 返回Mail对象
                $('#commentId').text(commentId);
                $('#fabulous').text(data.fabulous);
                $('.mailContent').text(data.content);
            }
        });
    });

    //点击删除按钮触发的事件
    $(".deleteComment").on("click", function () {
        // 得到订单id
        var commentId = $(this).data("id");
        $("#determineDeleteComment").data("id", commentId);
        $("#deleteCommentId").text(commentId);
    });

    // 点击确定删除触发
    $("#determineDeleteComment").on("click", function () {
        // 得到订单id
        var commentId = $(this).data("id");
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/deleteComment",
            data: {
                "id": commentId
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+commentId+"的邮件信息删除成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });
};