$('.musicList ul li').on('click', function () {
    let all = $('.musicList ul li');
    for (let i = 0; i < all.length; i++) {
        let className = this.className;
        console.log(this.className);
        if (className === 'active') {
            $(this).removeClass('active');
        } else {
            continue;
        }
    }
    console.log(this);
    $(this).addClass('active');
});

//音乐切换
$(".music_list").click(function () {
    //获取角标
    var index = $(this).index();
    console.log(index);
    //获取歌曲名
    var musicName = $(this).children()[1];
    musicName = musicName.innerHTML;
    //获取歌曲路径
    var musicSrc = $(".hide")[index].innerHTML;
    musicSrc = "../../static/" + musicSrc;
    //获取歌词
    var musicLyr = $(".hideLyr")[index].innerHTML;
    // musicLyr = musicLyr;
    //获取audio
    var autio = $("audio");
    //获取歌曲显示
    var displayMusicName = $(".Play .music_name h2")[0];
    console.log(musicSrc);
    //获取歌词显示
    var displayMusicLyr = $(".Play .Lyric")[0];

    //开始显示
    displayMusicName.innerHTML = musicName;
    displayMusicLyr.innerHTML = musicLyr;
    $(autio).attr('src', musicSrc);
    console.log($(autio).attr('src'));


//歌词同步--------------------------------------------------------
    var str = '';
    var lyric = musicLyric[0].lyric;
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

    var Original = 0;
    document.getElementById('audio').addEventListener('timeupdate', function () {
        // console.log(this.currentTime);
        var cur = parseInt(this.currentTime);
        if(document.getElementById('time' + cur)){
            document.getElementById('time' + Original).style.cssText = 'font-size: 18px;color: #666A70;';
            Original = cur;
            document.getElementById('time' + cur).style.cssText = 'font-size: 23px;color: #fff';
            // console.log(document.getElementById('time' + cur));
            let height = document.getElementById('time' + cur).offsetTop;
            console.log(height);
            if(height < 300){
            }else {
                $(".Lyric")[0].scrollTo(0,height-300);
            }
        }
        //歌词滚动
    }, false);



});


//歌词同步
var musicLyric = [
    {
        lyric: "[00:00.05]陈硕子 - 凌晨三点 (Demo)\n" +
            "[00:01.98]我在凌晨三点\n" +
            "[00:02.81]醒来的夜里\n" +
            "[00:04.70]想起了失去的你\n" +
            "[00:08.47]曾经说着永远一起\n" +
            "[00:12.07]现在却不再联系\n" +
            "[00:15.35]就算时间它模糊了很多的东西\n" +
            "[00:19.40]我依然在深爱着你\n" +
            "[00:22.91]如果当时的我们能少一些固执\n" +
            "[00:27.10]是否会有更好的结局\n" +
            "[00:30.49]我在凌晨三点\n" +
            "[00:32.82]醒来的夜里\n" +
            "[00:34.64]想起已失去的你\n" +
            "[00:38.39]曾经说着永远一起\n" +
            "[00:41.93]现在却不再联系\n" +
            "[00:45.12]就算时间它模糊了很多的东西\n" +
            "[00:49.12]我依然在深爱着你\n" +
            "[00:52.60]如果当时的我们能少一些固执\n" +
            "[00:56.69]是否会有更好的结局\n" +
            "[01:03.19]我在凌晨三点\n" +
            "[01:05.97]醒来的夜里\n" +
            "[01:07.79]想起已失去的你\n" +
            "[01:11.49]曾经说着永远一起\n" +
            "[01:15.13]现在却不再联系\n" +
            "[01:18.32]就算时间它模糊了很多的东西\n" +
            "[01:22.33]我依然地深爱着你\n" +
            "[01:25.72]如果当时的我们能少一些固执\n" +
            "[01:29.82]是否会有更好的结局"
    }
];



