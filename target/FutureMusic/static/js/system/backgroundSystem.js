//后台管理系统的js
$(document).ready(function () {
    // 点击下拉列表中的值触发
    $(".select").on("click", function () {
        $("#dropdownMenu1").text($(this).text()).append(" <span class=\"caret\"></span>");
        $("#type").val($(this).data("value"));
    });
<<<<<<< HEAD
=======
    // 用户退出登录
    $(".signOutLogin").on("click", function () {
        $.ajax({
            type: "get",
            url: "/signOutLogin",
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if(data.state===1){
                    window.location.href ="http://localhost:8080/index";
                }
            }
        });
    });
>>>>>>> 0ca05eefec8f2220ae7247ab5c20916769c5f895
});