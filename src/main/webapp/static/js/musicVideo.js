var url = window.location.search;
var musicVideoId = url.substring(url.lastIndexOf('musicVideoId=') + 13, url.length);
console.log('ID=' + musicVideoId);

var information = $(".music_video")[0].children[0].children[0]

$.ajax({
    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    url: "musicVideoInformation",
    type: 'post',
    dataType: "json",
    data: {musicVideoId: musicVideoId},
    success: function (data) {
        console.log("ok");
        console.log(data);
    }
});


// $(".ILike .ILikeMusicVideo").click(function () {
//     var mv = $(this).children()[0];
//     console.log(mv);
// });

$.ajax({
    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    url: "playMusicVideo",
    type: 'post',
    dataType: "json",
    data: {id: musicVideoId},
    success: function (data) {
        if (data.id == 0) {
            information.innerHTML = "对不起，我们还未获得这支MV的版权";
        } else if (data.id == 1) {
            information.innerHTML = "对不起，这支MV需要VIP授权播放";
        } else if (data.id == 2) {
            information.innerHTML = "对不起，您还没有购买这支MV";
        } else {
            information.innerHTML = data.name;
            var video = $("video");
            $(video).attr('src', data.path);
        }
    }
});


var type = 2;
var like = 0;
$('.icon-like').on('click', function () {
    let obj = $(this)[0];
    if (like === 0){
        $(obj).addClass('like');
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            url: "collectionMusic",
            type: 'post',
            dataType: "json",
            data: {
                musicId: musicVideoId,
                type: type
            },
            success: function (data) {
                console.log(data);
            }
        })
        like = 1;
    }else {
        $(obj).removeClass('like');
        like = 0;
    }
});