//后台管理系统的分类管理
window.onload = function () {
    //将菜单变色
    $("#classificationPage").css("background-color", "RGB(0,153,102)");

    // 点击添加和删除中的下拉列表中的键触发
    $(".selectClassification").on("click", function () {
        $(".classification").text($(this).text()).append(" <span class=\"caret\"></span>");
        $(".key").data("key", $(this).data("value"))
    });

    // 点击添加分类的保存执行该方法
    $("#preservationValue").on("click", function () {
        // 得到选择的key
        var key = $(this).data("key");
        // 得到输入value
        var value = $("#addValue").val();
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/addClassification",
            data: {
                "key": key,
                "value": value
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if (data.state === 0) {
                    $(".promptInformation").text(data.information);
                } else {
                    alert(value + "的分类添加成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });


    // 点击删除中下拉列表中的键触发
    $(".selectClassification").on("click", function () {
        // 得到选择的key
        var key = $("#deleteValue").data("key");
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/showClassificationValue",
            data: {
                "key": key
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if (data == null) {
                    $(".promptInformation").text("没有指定分类");
                } else {
                    $(".selectValue").remove();
                    $.each(data, function (index, item) {
                        $("#getValue").append(" <li  class=\"selectValue\"><a  onclick='aaa()' href=\"#\">" + item + "</a></li>");
                    })
                }
            }
        });
    });



    // 点击删除分类的保存执行该方法
    $("#deleteValue").on("click", function () {
        // 得到选择的key
        var key = $(this).data("key");
        // 得到选中value
        var value = $(this).data("value");
        $.ajax({
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            url: "/administrators/deleteClassification",
            data: {
                "key": key,
                "value": value
            },
            dataType: "json",
            success: function (data, status) {
                // 返回state
                if (data.state === 0) {
                    $(".promptInformation").text(data.information);
                } else {
                    alert(value + "的分类删除成功");
                    // 修改成功刷新网页
                    location.reload();
                }
            }
        });
    });
};

// 点击删除中的下拉列表中的值触发
function  aaa(){
    alert($(this).text());
    $("#value").text($(this).text()).append(" <span class=\"caret\"></span>");
    alert(22)
    alert($(this).html());
    $(".key").data("value", $(this).text());
    alert($(this).val());
}