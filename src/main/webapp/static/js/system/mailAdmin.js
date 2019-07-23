//后台管理系统的邮件管理
window.onload = function () {
    //将菜单变色
    $("#mailPage").css("background-color", "RGB(0,153,102)");

    // 点击更多按钮触发的事件
    $(".moreMail").on("click", function () {
        // 得到邮件id
        var mailId = $(this).data("id");
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/administrators/showIdMail?id=" + mailId,
            success: function (data, status) {
                // 返回Mail对象
                $('#mailId').text(mailId);
                $('.mailContent').text(data.content);
            }
        });
    });

    //点击删除按钮触发的事件
    $(".deleteMail").on("click", function () {
        // 得到订单id
        var mailId = $(this).data("id");
        $("#determineDeleteMail").data("id", mailId);
        $("#deleteMailId").text(mailId);
    });

    // 点击确定删除触发
    $("#determineDeleteMail").on("click", function () {
        // 得到订单id
        var mailId = $(this).data("id");
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/deleteMail",
            data: {
                "id": mailId
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+mailId+"的邮件信息删除成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });

};