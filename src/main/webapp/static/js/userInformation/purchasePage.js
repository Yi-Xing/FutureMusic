//支付页面
window.onload = function () {
    alert(2);
    $(".mes_nav").on("click", function () {
        // 得到音乐id
        var value = $(this).data("value");
        alert(value);
        if(value=="balance1"){
            window.location.href = 'http://localhost:8080/user/rechargeBalance?type=1';
        }else if(value=="balance2"){
            window.location.href = 'http://localhost:8080/user/rechargeBalance?type=2';
        }else if(value=="balance3"){
            window.location.href = 'http://localhost:8080/user/rechargeBalance?type=3';
        }else if(value=="vip1"){

        }else if(value=="vip2"){

        }else if(value=="vip3"){

        }else{
            alert("选择不合法");
        }

    });
};