$('.musicList ul li').on('click',function () {
    let all = $('.musicList ul li');
    for (let i = 0; i < all.length; i++) {
        let className = this.className;
        console.log(this.className);
        if (className === 'active'){
            $(this).removeClass('active');
        }else{
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
    musicSrc = "'" + "../../static/" + musicSrc + "'";
    //获取歌词
    var musicLyr = $(".hideLyr")[index].innerHTML;
    musicLyr = "'" + "" + musicLyr  + "'";
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
});