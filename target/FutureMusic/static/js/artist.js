//全部音乐人
window.onload = function () {
    // var artist_name = $(".artist .artist_bottom h4");   //音乐人姓名
    // var artist_img = $(".artists .artist_top img");   //音乐人头像
    // var artist_works = $(".artists .artist_top ol li a");   //音乐人作品*5
    var str = '';
    var tips = "";
    var tips1 = "<li>\n" +
        "<div class=\"artist\">\n" +
        "<div class=\"artist_top\">\n" +
        "<a href=\"javascript:;\" class=\"artist_left pull-left\">\n" +
        "<img th:src=\"@{/static/file/userHeadPortrait/";
    var artist_img_url;
    var tips2 = "}\" alt=\"\">\n" +
        "</a>\n" +
        "<div class=\"artist_right pull-right\">\n" +
        "<h5>音乐人作品</h5>\n" +
        "<ol>";
    var tips41 = "<li><a href=\"javascript:;\">";
    var artist_works;
    var tips42 = "</a></li>";
    var tips43 = "</ol>\n" +
        "</div>\n" +
        "</div>\n" +
        "<div class=\"artist_bottom\">\n" +
        "<a href=\"javascript:;\">\n" +
        "<h4>";
    var artist_name;
    var tips5 = "</h4>\n" +
        "</a>\n" +
        "</div>\n" +
        "</div>\n" +
        "</li>";
    $.ajax({
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        type: "post",
        url: "/allSinger",
        dataType: "json",
        success: function (data) {
            console.log(data);
            for (var i = 0; i < data.length; i++) {
                artist_img_url = data[i].portrait;
                artist_name = data[i].singerName;
                if (data[i].music.length < 4) {
                    for (var j = 0; j < data[i].music.length; j++) {
                        artist_works += tips41 + data[i].music[j] + tips42;
                    }
                } else {
                    for (var j = 0; j < 4; j++) {
                        artist_works += tips41 + data[i].music[j] + tips42;
                    }
                }
                console.log(artist_works);
                tips += tips1 + artist_img_url + tips2 + artist_works + tips43 + artist_name + tips5;
                str += tips;
                tips = '';
            }
            $(".artistS ul").innerHTML = str;
        }
    })
};
