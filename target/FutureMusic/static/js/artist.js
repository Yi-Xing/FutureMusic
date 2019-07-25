//全部音乐人
window.onload = function () {
    var artist_name = $(".artist .artist_bottom h4");   //音乐人姓名
    var artist_img = $(".artists .artist_top img");   //音乐人头像
    var artist_works = $(".artists .artist_top ol li a");   //音乐人作品*5
    console.log(artist_name);
    $.ajax({
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        type: "post",
        url: "/allSinger",
        dataType: "json",
        success: function (data) {
            console.log("ajax启动");
        }
    })
};
