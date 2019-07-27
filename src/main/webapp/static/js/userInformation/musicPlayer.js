var musicId =$("#musicId").data("value");
console.log('音乐ID=' + musicId);

//音乐切换
$(".music_list").click(function () {

    //ajax请求


    //获取角标
    var index = $(this).index();
    console.log("歌曲下标" + index);
    //获取歌曲名
    var musicName = $(this).children()[1];
    //获取对应歌曲的ID
    var thi = this.children[0];
    musicId = $(thi).data("id");
    console.log(musicId);

    //获取audio
    var autio = $("audio");
    //获取歌曲显示
    var displayMusicName = $(".Play .music_name h2")[0];
    // console.log("歌曲SRC" + musicSrc);
    //获取歌词显示
    var displayMusicLyr = $(".Play .Lyric")[0];
    musicTab();

//歌词同步--------------------------------------------------------
    var Original = 0;
    document.getElementById('audio').addEventListener('timeupdate', function () {
        // console.log(this.currentTime);
        var cur = parseInt(this.currentTime);
        if (document.getElementById('time' + cur)) {
            document.getElementById('time' + Original).style.cssText = 'font-size: 18px;color: #666A70;';
            Original = cur;
            document.getElementById('time' + cur).style.cssText = 'font-size: 23px;color: #fff';
            // console.log(document.getElementById('time' + cur));
            let height = document.getElementById('time' + cur).offsetTop;
            // console.log(height);
            if (height < 300) {
            } else {
                $(".Lyric")[0].scrollTo(0, height - 300);
            }
        }
        //歌词滚动
    }, false);


});


function musicTab() {
    var str = '';
    $.ajax({
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url: "playMusic",
        type: 'post',
        dataType: "json",
        data: {id: musicId},
        success: function (data) {
            console.log(data.id);
            console.log(data.name);
            console.log(data.path);
            console.log(data.picture);
            // console.log(data.lyricPath);

            //获取audio
            var autio = $("audio");
            $(autio).attr('src', data.path);
            //获取歌曲显示
            var displayMusicName = $(".Play .music_name h2")[0];
            displayMusicName.innerHTML = data.name;


            var lyric = data.lyricPath;
            var lyr = lyric.split("[");
            lyr.forEach(function (current) {
                // console.log(current);
                let geci = current.split(']');
                //关于时间的处理
                let time = geci[0];
                time = time.split(':');
                time1 = time[0];
                time2 = time[1];
                time = time1 * 60 + parseInt(time2);

                // console.log(time);
                // console.log(geci[1]);
                //undefined判断
                if (geci[1]) {
                    str += '<p id = time' + time + '>' + geci[1] + '</p>';
                }
            });
            $(".Lyric")[0].innerHTML = str;
        }
    });
}