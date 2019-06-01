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
    var musicName = $(this).children()[1];
    console.log(musicName);
});