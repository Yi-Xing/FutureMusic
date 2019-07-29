//邮箱页面
window.onload = function () {

    // 点击发送邮箱按钮触发的事件
    $("#send").on("click", function () {
        var mailbox=$("#mailbox").val();
        var content=$("#content").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/user/sendMailUser",
            data: {
                "mailbox": mailbox,
                "content": content
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    alert(data.information);
                }else {
                    alert("邮箱发送成功");
                    location.reload();
                }
            }
        });
    });


    // 点击发送邮箱给客服按钮触发的事件
    $("#sendCustomer").on("click", function () {
        var content=$("#customerContent").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/user/feedback",
            data: {
                "content": content
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    alert(data.information);
                }else {
                    alert("邮箱发送成功");
                    location.reload();
                }
            }
        });
    });


    // 点击发送全体邮箱按钮触发的事件
    $("#sendUser").on("click", function () {
        var content=$("#userContent").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/user/sendWhole",
            data: {
                "content": content
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    alert(data.information);
                }else {
                    alert("通知发送成功");
                    location.reload();
                }
            }
        });
    });
};
