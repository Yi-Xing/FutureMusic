var url = window.location.search;
var musicVideoId = url.substring(url.lastIndexOf('musicVideoId=') + 13, url.length);
console.log('ID='+musicVideoId);


$.ajax({
    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    url: "musicVideoInformation",
    type: 'post',
    dataType: "json",
    data: {musicVideoId:musicVideoId},
    success:function (data) {
        console.log("ok");
        console.log(data);
    }


});