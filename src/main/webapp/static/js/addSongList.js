var musicId;
// 点击音乐的右边的。。。中的添加到天厨模态框显示，显示用户所有的歌单
$(".add_Music").on("click", function () {
    musicId = $(this).data('id');
    console.log(musicId);
    $.ajax({
        type: "get",
        url: "/user/getSongList",
        dataType: "json",
        success: function (data, status) {
            console.log(data);
            for(var i in data){
                // 得到每个歌单的名字和ID
                var id=data[i].id;
                var name=data[i].name;
                //返回state
                console.log(name);
                // 循环编辑加上单选按钮

            }
            addSongList(data);
        }
    });
});

// 选中指定歌单后点击确定提交
$("#add_MusicInformation").on("click", function () {
    // 的到用户选中的单选按钮中的歌单Id
    var id= $('input:radio:checked').val();
    // alert(id);
    // alert(musicId);
    // 以及音乐的ID
    // var musicId=$("").val();
    $.ajax({
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        type: "post",
        url: "/user/SongListAddMusic",
        data: {
            "musicId": musicId,
            "type": 1,
            "belongId": id
        },
        dataType: "json",
        success: function (data, status) {
            //返回state
            if(data.state===0){
                alert("该音乐已在该歌单中");
            }else {
                alert("添加成功");
                // 修改成功刷新网页
                location.reload();
            }
        }
    });
});

// 在歌单详细信息中点击... 删除将指定音乐从歌单中删除
$(".icon-laji").on("click", function () {
    id = $(this).data('id');
    // 的到用户选中的单选按钮中的歌单Id
    musicId = $(this).children().data('id');
    alert(id);
    alert(musicId);
    // 以及音乐的ID
    $.ajax({
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        type: "post",
        url: "/user/SongListDeleteMusic",
        data: {
            "musicId": musicId,
            "type": 1,
            "belongId": id
        },
        dataType: "json",
        success: function (data, status) {
            //返回state
            if(data.state===0){
                alert("请重试");
            }else {
                alert("删除成功");
                // 修改成功刷新网页
                location.reload();
            }
        }
    });
});


function addSongList(songList) {
    var modalBody = $(".modal-body .form-group")[0];
    var tips = "";
    var songList = songList;    //数组
    var length = songList.length;
    //便离开始
    for (i = 0; i < length; i++) {

        var tips_1 = "<div class=\"col-sm-1\">\n" +
            "                                        <input type=\"radio\" name=\"songList\" value=\"";
        var songListId = songList[i].id;     //歌单i的id
        var tips_2 = "\">\n" +
            "                                    </div>\n" +
            "                                    <label class=\"col-sm-11 control-label\">";
        var songListName = songList[i].name;    //歌单i的名字
        var tips_3 = "</label>\n" +
            "                                    </br>\n" +
            "                                    </br>\n" +
            "                                    </br>";
        //遍历结束
        tips += tips_1 + songListId + tips_2 + songListName + tips_3;
    }
    modalBody.innerHTML = tips;
}