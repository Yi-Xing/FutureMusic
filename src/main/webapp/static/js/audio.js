
window.onload = function () {
    // 对于参数ev的学习
    // window.onclick = function(ev){
    //         if(!ev){ev = window.event;} //这句也可以简写成：ev=window.event||ev;
    //         alert(ev.pageX+","+ev.pageY);
    //     };

    var oAudio = document.getElementById("audio");
    var oPlay = document.getElementsByClassName("play")[0];
    var clickNum = 0;       //用于判断是要播放还是暂停
    var oProgress = document.getElementsByClassName("range")[0];
    var oMaxVolume=document.getElementsByClassName("volume_range")[0];
    var oProgress_circle = document.getElementsByClassName("circle")[0];
    var oVolume_circle=document.getElementsByClassName("circle")[1];
    var oCurrent = document.getElementsByClassName("current_time")[0];
    var oBofangModel = document.getElementsByClassName("bofangModel")[0];
    var clickNum3 = 0;
    var oNext=document.getElementsByClassName("icon-yduixiayiqu")[0];
    var oPre=document.getElementsByClassName("icon-yduishangyiqu")[0];
    var oMaxProgress=document.getElementsByClassName("Progress")[0];
    var oVolumeIcon=document.getElementsByClassName("volume")[0];
    var oVolume=document.getElementsByClassName("range")[1];

    //获取进度条的宽
    var ProgressWid = document.getElementsByClassName("progress_range")[0].clientWidth;
    var voiceWid = document.getElementsByClassName("volume_range")[0].clientWidth;


    function playSong(index) {
        localStorage.setItem("index", index);
        setInfo();
        oAudio.play();
        oPlay.innerHTML = "<i class='iconfont icon-bofang' title='暂停'></i>";
        clickNum = 1;
        // oNeedle.style.animation = "rotate-needle-resume 0.5s 1 normal linear forwards;";
        // oDisk.animationPlayState = "running";
        setInterval(setProgress, 1000);
    }

//播放与暂停按钮的设置
    oPlay.onclick = function () {
        if (clickNum == 0) {
            oAudio.play();
            setInterval(setProgress, 1000);
            oPlay.innerHTML = "<i class='iconfont icon-zanting' title='播放'></i>";
            clickNum = 1;
            // oNeedle.style.transform = "rotate(0deg)";
            // oDisk.style.animationPlayState = "running";
        } else {
            oAudio.pause();
            oPlay.innerHTML = "<i class='iconfont icon-bofang' title='暂停'></i>";
            clickNum = 0;
            // oNeedle.style.transform = "rotate(-25deg)";
            // oDisk.style.animationPlayState = "paused";
        }
    };

//下一首按钮的点击
    oNext.onclick = function () {
        var next = (parseInt(localStorage.getItem("index")) + 1) % oMusic.length;
        if (clickNum == 0) {
            localStorage.setItem("index", next);
            setInfo();
        } else {
            playSong(next);
        }
    };
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
        var l = ev.clientX - 788;          //获取圆距左端的距离
        if (l < 0) {
            l = 0;
        } else if (l > ProgressWid) {
            l = ProgressWid - 10;
        }
        localStorage.setItem("progress", l);
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
        var l = ev.clientX - 1172;          //获取圆距foot左端的距离
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
};