var url = window.location.search;
var musicId = url.substring(url.lastIndexOf('=') + 1, url.length);
console.log('音乐ID=' + musicId);
var type = 2;

$(".icon-like").on('click', function () {
    collectionMusic(musicId, type, $(this));
});

function collectionMusic(musicId, type, obj) {
    $.ajax({
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url: "/user/collectionMusic",
        type: 'post',
        dataType: "json",
        data: {
            musicId: musicId,
            type: type
        },
        success: function (data) {
            console.log(data);
            if (data.state == 1) {
                alert("删除收藏成功")
                // alert("已取消收藏！");
                // $(obj).removeClass('like');
            } else if (data.state == 2) {
                alert("添加收藏成功")
                // $(obj).addClass('like');
            } else {
                alert("请刷新网页")
            }

        }
    });

}