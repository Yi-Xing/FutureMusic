var url = window.location.search;
var musicId = url.substring(url.lastIndexOf('=') + 1, url.length);
console.log('音乐ID='+musicId);
$.ajax({
    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    url: "showSongListDetail",
    type: 'post',
    data: {musicId:musicId},
    dataType: "json",
    success:function (data) {
        console.log(data);
    },
    error:function () {
        console.log("错误!")
    }
});