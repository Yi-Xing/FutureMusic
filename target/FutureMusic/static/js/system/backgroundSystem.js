//后台管理系统的js
$(document).ready(function () {
    // 点击下拉列表中的值触发
    $(".select").on("click", function () {
        $("#dropdownMenu1").text($(this).text()).append(" <span class=\"caret\"></span>");
        $("#type").val($(this).data("value"));
    });
});