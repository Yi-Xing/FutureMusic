var url = window.location.search;
var musicVideoId = url.substring(url.lastIndexOf('musicVideoId=') + 13, url.length);
// console.log('ID=' + musicVideoId);

var information = $(".music_video")[0].children[0].children[0];
var Information = $(".music_video")[0].children[0].children[1];

var obj_id = $(".ILike h2")[0];
musicVideoId = $(obj_id).data("id");
// console.log(musicVideoId);
playMusicVideo(musicVideoId);

var payUrl = "/user/musicPage?id=" + musicVideoId + "&type=2";
var vipUrl = "/user/vipPage?id=" + musicVideoId + "&type=2";

function playMusicVideo(musicVideoId) {

    $.ajax({
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url: "/user/playMusicVideo",
        type: 'post',
        dataType: "json",
        data: {id: musicVideoId},
        success: function (data) {
            if (data.id == 0) {
                Information.innerHTML = "对不起，我们还未获得这支MV的版权";
            } else if (data.id == 1) {
                Information.innerHTML = "对不起，这支MV需要VIP授权播放";
                $(".payMusic").attr("href", vipUrl);
            } else if (data.id == 2) {
                Information.innerHTML = "对不起，您还没有购买这支MV";
                $(".payMusic").attr("href", payUrl);
            } else {
                $(".payMusic").hide();
            }
            information.innerHTML = data.name;
            var video = $("video");
            $(video).attr('src', data.path);
            // alert(data.classificationId);
            if (data.classificationId == 1) {
                $($(".icon-like")).addClass("like");
            }
        }
    });
}

// var type = 2;
// var like = 0;
// $('.icon-like').on('click', function () {
//     let obj = $(this)[0];
//     if (like === 0) {
//         $(obj).addClass('like');
//         $.ajax({
//             contentType: "application/x-www-form-urlencoded;charset=UTF-8",
//             url: "collectionMusic",
//             type: 'post',
//             dataType: "json",
//             data: {
//                 musicId: musicVideoId,
//                 type: type
//             },
//             success: function (data) {
//                 console.log(data);
//             }
//         });
//         like = 1;
//     } else {
//         $(obj).removeClass('like');
//         like = 0;
//     }
// });



