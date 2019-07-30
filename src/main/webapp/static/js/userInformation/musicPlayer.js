// 对于参数ev的学习
// window.onclick = function(ev){
//         if(!ev){ev = window.event;} //这句也可以简写成：ev=window.event||ev;
//         alert(ev.pageX+","+ev.pageY);
//     };

var oAudio = document.getElementById("audio");
var oPlay = document.getElementsByClassName("play")[0];
var clickNum = 0;       //用于判断是要播放还是暂停
var oProgress = document.getElementsByClassName("range")[0];
var oMaxVolume = document.getElementsByClassName("volume_range")[0];
var oProgress_circle = document.getElementsByClassName("circle")[0];
var oVolume_circle = document.getElementsByClassName("circle")[1];
var oCurrent = document.getElementsByClassName("current_time")[0];
var oBofangModel = document.getElementsByClassName("bofangModel")[0];
var clickNum3 = 0;
var oNext = document.getElementsByClassName("icon-yduixiayiqu")[0];
var oPre = document.getElementsByClassName("icon-yduishangyiqu")[0];
var oMaxProgress = document.getElementsByClassName("Progress")[0];
var oVolumeIcon = document.getElementsByClassName("volume")[0];
var oVolume = document.getElementsByClassName("range")[1];

//获取进度条和音量条的宽
var ProgressWid = document.getElementsByClassName("progress_range")[0].clientWidth;
var voiceWid = document.getElementsByClassName("volume_range")[0].clientWidth;
//获取进度条和音量条的位置
var Progressleft = document.getElementsByClassName("progress_range")[0];
var ProgressLeft = getOffsetWidthByBody(Progressleft);
var voiceleft = document.getElementsByClassName("volume_range")[0];
var voiceLeft = getOffsetWidthByBody(voiceleft);
// console.log(ProgressLeft);
var index;      //用于切换音乐
var length;


// 元素相对于body的offsetTop
function getOffsetWidthByBody(el) {
    let offsetWidth = 0;
    while (el && el.tagName !== 'BODY') {
        offsetWidth += el.offsetLeft;
        el = el.offsetParent;
    }
    return offsetWidth;
}


function playSong(index) {
    // localStorage.setItem("index", index);
    // setInfo();
    oAudio.play();
    console.log("播放");

    oPlay.innerHTML = "<i class='iconfont icon-bofang' title='暂停'></i>";
    clickNum = 1;
    setInterval(setProgress, 1000);
}

//播放与暂停按钮的设置
oPlay.onclick = function () {

    if (clickNum == 0) {
        oAudio.play();
        setInterval(setProgress, 1000);
        oPlay.innerHTML = "<i class='iconfont icon-zanting' title='播放'></i>";
        clickNum = 1;
    } else {
        oAudio.pause();
        oPlay.innerHTML = "<i class='iconfont icon-bofang' title='暂停'></i>";
        clickNum = 0;
    }
};

//下一首按钮的点击
oNext.onclick = function () {
    console.log("下一首");
    var next = index + 1;
    if (clickNum == 1) {
        playSong(next);
    }
};
oPre.onclick = function () {
// var next = index;
    console.log("上一首");
//上一首按钮的点击事件
    var pre = index - 1;
    if (clickNum == 1) {        //未播放只设置信息
        playSong(pre);
    }
};

//设置进度的自动移动
function setProgress() {
    oCurrent.innerHTML = format(oAudio.currentTime);  //当前时间
    oProgress.style.width = (oAudio.currentTime) / (oAudio.duration) * ProgressWid - 10 + "px";
    oProgress_circle.style.left = oProgress.style.width;
}

//可以点击轨道改变进度
oMaxProgress.onmousedown = function (ev) {
    ev = ev || event;
    changeProgress(ev);
};
//鼠标拖动小圆改变进度
oProgress_circle.onmousedown = function (ev) {
    document.onmousemove = function (ev) {
        changeProgress(ev);
    };
    document.onmouseup = function () {      //当鼠标松开后关闭移动事件和自身事件
        document.onmousemove = null;
        document.onmouseup = null;
    };
    return false;
};

function changeProgress(ev) {
    ev = ev || event;
    var l = ev.clientX - ProgressLeft;          //获取圆距左端的距离
    if (l < 0) {
        l = 0;
    } else if (l > ProgressWid) {
        l = ProgressWid - 10;
    }
    // localStorage.setItem("progress", l);
    oProgress_circle.style.left = l + "px";
    oProgress.style.width = l + "px";
    oAudio.currentTime = (l / ProgressWid) * oAudio.duration;
    oCurrent.innerHTML = format(oAudio.currentTime);  //当前时间
}

//时间的设置,点击轨道
oMaxVolume.onmousedown = function (ev) {
    ev = ev || event;
    changeVolume(ev);
};
//拖动小圆
oVolume_circle.onmousedown = function (ev) {
    document.onmousemove = function (ev) {
        changeVolume(ev);
    };
    document.onmouseup = function () {      //当鼠标松开后关闭移动事件和自身事件
        document.onmousemove = null;
        document.onmouseup = null;
    };
    return false;
};

function changeVolume(ev) {
    var ev = ev || event;
    var l = ev.clientX - voiceLeft;          //获取圆距foot左端的距离
    if (l < 0) {
        l = 0;
    } else if (l > voiceWid) {
        l = voiceWid;
    }
    oVolume_circle.style.left = l - 5 + "px";
    oVolume.style.width = l - 5 + "px";
    oAudio.volume = l / voiceWid;
    localStorage.setItem("volume", l);
    if (l == 0) {
        oVolumeIcon.innerHTML = "<span class='iconfont icon-yinliangxiaoyinliangxiao' title='恢复音量'></span>";
        clickNum1 = 1;
    } else {
        oVolumeIcon.innerHTML = "<span class='iconfont icon-yinliangdayinliangda' title='静音'></span>";
        clickNum1 = 0;
    }
}

//静音与恢复音量的设置
oVolumeIcon.onclick = function () {
    if (clickNum1 == 0) {
        oVolumeIcon.innerHTML = "<span class='iconfont icon-yinliangxiaoyinliangxiao' title='恢复音量'></span>";
        oVolume_circle.style.left = "0px";
        oVolume.style.width = "0px";
        oAudio.volume = 0;
        clickNum1 = 1;
    } else {
        oVolumeIcon.innerHTML = "<span class='iconfont icon-yinliangdayinliangda' title='静音'></span>";
        var l = localStorage.getItem("volume");
        if (l == 0)
            l += 1;
        oVolume_circle.style.left = l + "px";
        oVolume.style.width = l + "px";
        oAudio.volume = l / voiceWid;
        clickNum1 = 0;
    }
};

//时间的格式化
function format(t) {
    var m = Math.floor(t / 60);
    var s = Math.floor(t % 60);
    if (m <= 9)
        m = "0" + m;
    if (s <= 9)
        s = "0" + s;
    return m + ":" + s;
}

//刚加载，clickNum3=0，不触发点击事件
if (clickNum3 == 0) {
    oAudio.loop = false;
    oAudio.addEventListener("ended", suiji, false);     //监听函数不能加括号
}
//播放模式的切换
oBofangModel.onclick = function () {
    if (clickNum3 == 0) {
        oBofangModel.innerHTML = "<span class='iconfont icon-xunhuan' title='列表循环'></span>";
        clickNum3 = 1;
        oAudio.loop = false;
        // oAudio.removeEventListener("ended",function () {},false);       //匿名取消事件无效
        oAudio.removeEventListener("ended", suiji, false);
        oAudio.addEventListener("ended", liebiao, false);
    } else if (clickNum3 == 1) {
        oBofangModel.innerHTML = "<span class='iconfont icon-danquxunhuan' title='单曲循环'></span>";
        clickNum3 = 2;
        oAudio.loop = true;
    } else if (clickNum3 == 2) {
        oBofangModel.innerHTML = "<span class='iconfont icon-suijibofang' title='随机播放'></span>";
        clickNum3 = 0;
        if (oAudio != null) {
            oAudio.loop = false;
            oAudio.removeEventListener("ended", liebiao, false);
            oAudio.addEventListener("ended", suiji, false);
        }
    }
};

//列表循环，触发下一首的点击事件
function liebiao() {
    oNext.onclick();
}

//产生随机数，自动播放
function suiji() {
    var m = Math.floor(Math.random() * oMusic.length);//产生随机数，范围为0到oMusic.length-1,
    playSong(m);
}


//-------------------------------------------------------------------------------


var musicId;
musicId = $("#musicId").data("value");
// console.log('音乐ID=' + musicId);
index = $(".music_list").length - 1;
console.log("列表长度="+$(".music_list").length);


//音乐切换
$(".music_list").click(function () {

    oProgress_circle.style.left = 0 + "px";
    oProgress.style.width = 0 + "px";
    clickNum = 1;
    oPlay.innerHTML = "<i class='iconfont icon-bofang' title='暂停'></i>";


    //获取角标
    index = $(this).index();
    console.log("歌曲下标=" + index);
    //获取歌曲名
    var musicName = $(this).children()[1];
    //获取对应歌曲的ID
    var thi = this.children[0];
    musicId = $(thi).data("id");

    //获取audio
    var autio = $("audio");
    //获取歌曲显示
    var displayMusicName = $(".Play .music_name h2")[0];
    //获取歌词显示
    var displayMusicLyr = $(".Play .Lyric")[0];
    musicTab();
    monitor();
});


//歌词同步--------------------------------------------------------

function monitor() {
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
}

function musicTab() {
    var str = '';
    $.ajax({
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url: "playMusic",
        type: 'post',
        dataType: "json",
        data: {id: musicId},
        success: function (data) {

            //获取歌曲显示
            var displayMusicName = $(".Play .music_name h2")[0];
            displayMusicName.innerHTML = data.name;

            if (data.id == 0) {
                $(".Lyric")[0].innerHTML = "对不起，我们还未获得这首音乐的版权";
            } else if (data.id == 1) {
                $(".Lyric")[0].innerHTML = "对不起，这首歌需要VIP授权播放" + "<br><a href='/user/vipPage'>" + "点此进入充值购买页面" + '</a>';
            } else if (data.id == 2) {
                $(".Lyric")[0].innerHTML = "对不起，您还没有购买这首歌曲" + "<br><a href='/user/musicPage?id="+ musicId + "&type=1" + "'>" + "点此进入充值购买页面" + '</a>';
            } else if (data.id == 3) {
                $(".Lyric")[0].innerHTML = "对不起，此音乐没有歌词";
            } else {

                //获取audio
                var autio = $("audio");
                $(autio).attr('src', data.path);



                var lyric = data.lyricPath;
                var lyr = lyric.split("[");
                lyr.forEach(function (current) {
                    let geci = current.split(']');
                    //关于时间的处理
                    let time = geci[0];
                    time = time.split(':');
                    time1 = time[0];
                    time2 = time[1];
                    time = time1 * 60 + parseInt(time2);

                    //undefined判断
                    if (geci[1]) {
                        str += '<p id = time' + time + '>' + geci[1] + '</p>';
                    }
                });
                $(".Lyric")[0].innerHTML = str;
            }
        }
    });
}

// var type = 1;
// var like = 0;
// $('.icon-like').on('click', function () {
//     let obj = $(this)[0];
//     if (like === 0){
//         $(obj).addClass('like');
//         like = 1;
//         $.ajax({
//             contentType: "application/x-www-form-urlencoded;charset=UTF-8",
//             url: "collectionMusic",
//             type: 'post',
//             dataType: "json",
//             data: {
//                 musicId: musicId,
//                 type: type
//             },
//             success: function (data) {
//                 console.log(data);
//             }
//         })
//     }else {
//         $(obj).removeClass('like');
//         like = 0;
//     }
// });


function PLAY(index) {

}