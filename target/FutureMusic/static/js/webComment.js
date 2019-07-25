var dianzan_num = 0;
$(".comment_container .comment_content .good .dianzan").click(function () {
    console.log(123);
    let dianzan = this.children[0];
    let suc = this.children[1];
    if (dianzan_num === 0) {
        $(dianzan).hide();
        $(suc).show();
        dianzan_num = 1;
    } else {
        $(suc).hide();
        $(dianzan).show();
        dianzan_num = 0;
    }
});
$(".comment_container .comment_content .good .icon-comment").click(function () {
    let obj = $(this).parent().parent().parent().parent().parent().parent()[0].children[2];
    $(obj).slideToggle(200);
});