//支付页面
window.onload = function () {


    $(".ewm").on("click", function () {
        // 得到音乐id
        var value = $(this).data("value");
        if(value==="balance1"){
            window.location.href = 'http://localhost:8080/user/rechargeBalance?type=1';
        }else if(value==="balance2"){
            window.location.href = 'http://localhost:8080/user/rechargeBalance?type=2';
        }else if(value==="balance3"){
            window.location.href = 'http://localhost:8080/user/rechargeBalance?type=3';
        }else if(value==="vip1"){
            value=1;
        }else if(value==="vip2"){
            value=6;
        }else if(value==="vip3"){
            value=10;
        }else{
            alert("选择不合法");
        }
        if(value===1 ||value===6 ||value===10 ){
            $.ajax({
                contentType: "application/x-www-form-urlencoded",
                type: "post",
                url: "/user/rechargeVIP",
                data: {
                    "count": value,
                },
                dataType: "json",
                success: function (data, status) {
                    // 返回state
                    if(data.state===0){
                        alert(data.information);
                    }else {
                        alert("开通vip成功");
                        location.reload();
                    }
                }
            });
        }
    });


    $("#purchase").on("click", function () {
        var id=$(this).data("id");
        var type=$(this).data("type");
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/user/purchase",
            data: {
                "type": type,
                "id": id
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===0){
                    alert(data.information);
                }else {
                    alert("购买成功");
                    location.reload();
                }
            }
        });
    });
};