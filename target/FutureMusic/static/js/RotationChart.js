var carousel = $('ul.carousel'),
    arrowLeft = $('a.arrow-left'),
    arrowRight = $('a.arrow-right'),
    indicators = $('li.indicator'),
    carouselWrap = $('div.carousel-wrap');

var num = 5, carouselWidth = 1200, count = 1, timer = null;

//设置左右箭头的点击事件

arrowLeft.click(function (e) {

    e.preventDefault();
    move(true)
});
arrowRight.click(function (e) {
    e.preventDefault();
    move()
});

// 指示器
indicators.click(function () {
    count = $(this).index();
    setIndicatorStyle();
    carousel.finish().animate({left: -carouselWidth * count}, 500)
});

// 设置指示器样式
function setIndicatorStyle() {
    indicators.eq(count - 1).addClass('active').siblings().removeClass('active')
}

interval();
// 鼠标移入  暂停 自动轮播
carouselWrap
    .mouseover(function () {
        clearInterval(timer)
    })
    .mouseout(interval);   // 鼠标移开 记录轮播

// 动画 主函数
function move(flag) {
    //向左滑动
    if (flag !== true) {
        count++;
        // console.log(count);
        let l = -carouselWidth * count;
        if (count === num - 1) {
            carousel.finish().animate({
                left: l
            }, 500, function () {
                count = 1;
                setIndicatorStyle();
                $(this).css('left', -carouselWidth * count)
            })
        } else {
            setIndicatorStyle();
            carousel.finish().animate({
                left: l
            }, 500)
        }
    } else {//向右滑动
        count--;
        // console.log(count);
        let l = -carouselWidth * count;
        if (count === 0) {
            carousel.finish().animate({
                left: l
            }, 500, function () {
                count = num - 2;
                setIndicatorStyle();
                $(this).css('left', -carouselWidth * count)
            })
        } else {
            setIndicatorStyle();
            carousel.finish().animate({
                left: l
            }, 500)
        }
    }
}

// 自动轮播
function interval() {
    timer = setInterval(move, 9999999)
}
