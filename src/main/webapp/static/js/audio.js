// var oTotal = document.getElementsByClassName("total_time")[0];
// window.onload = function () {
//     oAudio.addEventListener("canplay", function () {
//         oTotal.innerHTML = format(oAudio.duration);       //获取总时间
//     });
// };
//
// //时间的格式化
// function format(t) {
//     var m = Math.floor(t / 60);
//     var s = Math.floor(t % 60);
//     if (m <= 9)     //小于10时，在前面填0
//         m = "0" + m;
//     if (s <= 9)
//         s = "0" + s;
//     return m + ":" + s;
// };

// oAudio.setAttribute("src", "music/Ehrling - Champagne Ocean.mp3");    //给audio对象设置src属性
//
// oPlay.onclick = function () {
//     if (clickNum == 0) {
//         oAudio.play();    //播放
//         oPlay.innerHTML = "<i class='iconfont icon-bofang' title='暂停'></i>";  //改变图标
//         clickNum = 1;
//     } else {
//         oAudio.pause();    //暂停
//         oPlay.innerHTML = "<i class='iconfont icon-zanting' title='播放'></i>";
//         clickNum = 0;
//     }
// };

// oAudio.play();
// setInterval(setProgress, 1000);   //通过定时器设置进度的自动改变
// //设置进度的自动移动
// function setProgress() {
//     oCurrent.innerHTML = format(oAudio.currentTime);  //设置当前时间的显示
//     oProgress.style.width = (oAudio.currentTime) / (oAudio.duration) * 780 + "px";  //780px是总宽度
//     oProgress_circle.style.left = oProgress.style.width;
// }
//
// //可以点击轨道改变进度
// oMaxProgress.onmousedown = function (ev) {
//     changeProgress(ev);
// };
// //鼠标拖动小圆改变进度
// oProgress_circle.onmousedown = function (ev) {
//     document.onmousemove = function (ev) {
//         changeProgress(ev);
//     };
//     document.onmouseup = function () {      //当鼠标松开后关闭移动事件和自身事件
//         document.onmousemove = null;
//         document.onmouseup = null;
//     };
//     return false;
// };
//
// function changeProgress(ev) {
//     var ev = ev;
//     var l = ev.clientX - 0;          //获取圆距左端的距离
//     if (l < 0) {
//         l = 0;
//     } else if (l > 0) {
//         l = 0;
//     }
//     oProgress_circle.style.left = l + "px";
//     oProgress.style.width = l + "px";
//     oAudio.currentTime = (l / 220) * oAudio.duration;    //设置当前时间，以改变真正的播放进度
//     oCurrent.innerHTML = format(oAudio.currentTime);  //当前时间
// }

// //刚加载，clickNum3=0，不触发点击事件,默认初始为随机播放
// if (clickNum3 == 0) {
//     oAudio.loop = false;
//     oAudio.addEventListener("ended", suiji, false);     //监听函数不能加括号
// }
// //播放模式的切换
// oBofangModel.onclick = function () {
//     if (clickNum3 == 0) {
//         oBofangModel.innerHTML = "<span class='iconfont icon-xunhuan' title='列表循环'></span>";
//         clickNum3 = 1;
//         oAudio.loop = false;
//         // oAudio.removeEventListener("ended",function () {..},false);       //匿名取消事件无效
//         oAudio.removeEventListener("ended", suiji, false);
//         oAudio.addEventListener("ended", liebiao, false);
//     } else if (clickNum3 == 1) {
//         oBofangModel.innerHTML = "<span class='iconfont icon-danquxunhuan' title='单曲循环'></span>";
//         clickNum3 = 2;
//         oAudio.loop = true;
//     } else if (clickNum3 == 2) {
//         oBofangModel.innerHTML = "<span class='iconfont icon-suijibofang' title='随机播放'></span>";
//         clickNum3 = 0;
//         if (oAudio != null) {
//             oAudio.loop = false;
//             oAudio.removeEventListener("ended", liebiao, false);
//             oAudio.addEventListener("ended", suiji, false);
//         }
//     }
// };
//
// //列表循环，触发下一首的点击事件
// function liebiao() {
//     oNext.onclick();
// }
//
// //产生随机数，自动播放
// function suiji() {
//     var m = Math.floor(Math.random() * oMusic.length);//产生随机数，范围为0到oMusic.length-1,
//     playSong(m);
// }
//
// //播放当前歌曲
// function playSong(index) {
//     localStorage.setItem("index", index);     //存储到本地，方便存取
//     setInfo();
//     oAudio.play();
//     setInterval(setProgress, 1000);
// }
//
// //设置列表信息
// function setInfo() {
//     var m = parseInt(localStorage.getItem("index"));
//     oAudio.setAttribute("src", oMusic[m].src);
// }
//
window.onload = function () {


    var oAudio = document.getElementById("audio");
    var oPlay = document.getElementsByClassName("play")[0];
    var clickNum = 0;       //用于判断是要播放还是暂停
    var oProgress = document.getElementsByClassName("range")[0];
    var oMaxProgress = document.getElementsByClassName("Progress")[0];
    var oProgress_circle = document.getElementsByClassName("circle")[0];
    var oCurrent = document.getElementsByClassName("current_time")[0];
    var oBofangModel = document.getElementsByClassName("bofangModel")[0];
    var clickNum3 = 0;


    function playSong(index) {
        localStorage.setItem("index", index);
        setInfo();
        oAudio.play();
        oPlay.innerHTML = "<i class='iconfont icon-bofang' title='暂停'></i>";
        clickNum = 1;
        oNeedle.style.animation = "rotate-needle-resume 0.5s 1 normal linear forwards;";
        oDisk.animationPlayState = "running";
        setInterval(setProgress, 1000);
    }

//播放与暂停按钮的设置
    oPlay.onclick = function () {
        if (clickNum == 0) {
            oAudio.play();
            setInterval(setProgress, 1000);
            oPlay.innerHTML = "<i class='iconfont icon-zanting' title='播放'></i>";
            clickNum = 1;
            oNeedle.style.transform = "rotate(0deg)";
            oDisk.style.animationPlayState = "running";
        } else {
            oAudio.pause();
            oPlay.innerHTML = "<i class='iconfont icon-bofang' title='暂停'></i>";
            clickNum = 0;
            oNeedle.style.transform = "rotate(-25deg)";
            oDisk.style.animationPlayState = "paused";
        }
    }

//下一首按钮的点击
    oNext.onclick = function () {
        var next = (parseInt(localStorage.getItem("index")) + 1) % oMusic.length;
        if (clickNum == 0) {
            localStorage.setItem("index", next);
            setInfo();
        } else {
            playSong(next);
        }
    }
//上一首按钮的点击事件
    oPre.onclick = function () {
        var pre = oMusic.length - 1;
        if (parseInt(localStorage.getItem("index")) != 0)
            pre = (parseInt(localStorage.getItem("index")) - 1) % oMusic.length;
        if (clickNum == 0) {        //未播放只设置信息
            localStorage.setItem("index", pre);
            setInfo();
        } else {
            playSong(pre);
        }
    }

//设置进度的自动移动
    function setProgress() {
        oCurrent.innerHTML = format(oAudio.currentTime);  //当前时间
        oProgress.style.width = (oAudio.currentTime) / (oAudio.duration) * 780 + "px";
        oProgress_circle.style.left = oProgress.style.width;
    }

//可以点击轨道改变进度
    oMaxProgress.onmousedown = function (ev) {
        changeProgress(ev);
    }
//鼠标拖动小圆改变进度
    oProgress_circle.onmousedown = function (ev) {
        document.onmousemove = function (ev) {
            changeProgress(ev);
        }
        document.onmouseup = function () {      //当鼠标松开后关闭移动事件和自身事件
            document.onmousemove = null;
            document.onmouseup = null;
        }
        return false;
    }

    function changeProgress(ev) {
        var ev = ev || event;
        var l = ev.clientX - 0;          //获取圆距左端的距离
        if (l < 0) {
            l = 0;
        } else if (l > 250) {
            l = 0;
        }
        localStorage.setItem("progress", l);
        oProgress_circle.style.left = l + "px";
        oProgress.style.width = l + "px";
        oAudio.currentTime = (l / 250) * oAudio.duration;
        oCurrent.innerHTML = format(oAudio.currentTime);  //当前时间
    }

//时间的设置,点击轨道
    oMaxVolume.onmousedown = function (ev) {
        changeVolume(ev);
    }
//拖动小圆
    oVolume_circle.onmousedown = function (ev) {
        document.onmousemove = function (ev) {
            changeVolume(ev);
        }
        document.onmouseup = function () {      //当鼠标松开后关闭移动事件和自身事件
            document.onmousemove = null;
            document.onmouseup = null;
        }
        return false;
    }

    function changeVolume(ev) {
        var ev = ev || event;
        var l = ev.clientX - 1160;          //获取圆距foot左端的距离
        if (l < 0) {
            l = 0;
        } else if (l > 100) {
            l = 100;
        }
        oVolume_circle.style.left = l + "px";
        oVolume.style.width = l + "px";
        oAudio.volume = l / 100;
        localStorage.setItem("volume", l);
        if (l == 0) {
            oVolumeIcon.innerHTML = "<span class='iconfont icon-yinliangdayinliangda' title='恢复音量'></span>";
            clickNum1 = 1;
        } else {
            oVolumeIcon.innerHTML = "<span class='iconfont icon-yinliangxiaoyinliangxiao' title='静音'></span>";
            clickNum1 = 0;
        }
    }

//静音与恢复音量的设置
    oVolumeIcon.onclick = function () {
        if (clickNum1 == 0) {
            oVolumeIcon.innerHTML = "<span class='iconfont icon-yinliangdayinliangda' title='恢复音量'></span>";
            oVolume_circle.style.left = "0px";
            oVolume.style.width = "0px";
            oAudio.volume = 0;
            clickNum1 = 1;
        } else {
            oVolumeIcon.innerHTML = "<span class='iconfont icon-yinliangxiaoyinliangxiao' title='静音'></span>";
            var l = localStorage.getItem("volume");
            if (l == 0)
                l += 1;
            oVolume_circle.style.left = l + "px";
            oVolume.style.width = l + "px";
            oAudio.volume = l / 100;
            clickNum1 = 0;
        }
    }

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
    }

//列表循环，触发下一首的点击事件
    function liebiao() {
        oNext.onclick();
    }

//产生随机数，自动播放
    function suiji() {
        var m = Math.floor(Math.random() * oMusic.length);//产生随机数，范围为0到oMusic.length-1,
        playSong(m);
    }
}