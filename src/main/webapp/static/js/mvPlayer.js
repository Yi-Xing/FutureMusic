var url = window.location.search;
var musicId = url.substring(url.lastIndexOf('=') + 1, url.length);
console.log('音乐ID='+musicId);
$.ajax({
    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    url: "searchMusicVideoByName",
    type: 'post',
    // dataType: "json",
    data: {musicId:musicId},
    success:function (data) {
        console.log(data);
    }
});