var url = window.location.search;
var musicId = url.substring(url.lastIndexOf('musicId=') + 8, url.length);
console.log('音乐ID='+musicId);
var Name = $(".name a h1")[0];
var Artist = $(".name a h5 b")[0];
var Bofangliang = $(".name a h5")[1];
var Shoucangliang = $(".name a h5")[2];
var Touxiang = $(".personal_information img")[0];
var Tname = $(".table-striped .music_list td")[0];
var Tartistname = $(".table-striped .music_list td")[1];
var Talbum = $(".table-striped .music_list td")[2];
$.ajax({
    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    url: "showMusicDetail",
    type: 'post',
    dataType: "json",
    data: {musicId:musicId},
    success:function (data) {
        console.log(data);
        console.log(Artist);
        console.log(Tname);
        console.log(Tartistname);
        //歌名
        var name = data.music.name;
        Name.innerText = name;
        Tname.innerHTML = name;
    //    作者
        var artist = data.singer.name;
        Artist.innerHTML = artist;
        Tartistname.innerHTML = artist;
    //    播放量
        var bofangliang = data.musicPlayCount;
        Bofangliang.innerHTML = "播放量:" + bofangliang;
    //    收藏量
        var shoucangliang = data.musicCollectCountmusicPlayCount;
        Shoucangliang.innerHTML = "收藏量:" + shoucangliang;
    //    头像
        var touxiang = data.music.picture;
        console.log(touxiang);
        Touxiang.src = touxiang;
    //    专辑
        var album = data.album.name;
        Talbum.innerHTML = album;


    }
});

$(Tname).click(function () {
    window.location.href = "musicPlay.html?musicId=" + musicId;
})