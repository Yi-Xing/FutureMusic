//后台管理系统的订单管理
window.onload = function () {
    //将菜单变色
    $("#orderPage").css("background-color", "RGB(0,153,102)");

    //点击删除按钮触发的事件
    $(".deleteOrder").on("click", function () {
        // 得到订单id
        var orderId = $(this).data("id");
        $("#determineDeleteOrder").data("id", orderId);
        $("#deleteOrderId").text(orderId);
    });

    // 点击确定删除触发
    $("#determineDeleteOrder").on("click", function () {
        // 得到订单id
        var orderId = $(this).data("id");
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/deleteOrder",
            data: {
                "id": orderId
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    $(".promptInformation").text(data.information);
                }else {
                    alert("id:"+orderId+"的订单信息删除成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });

};