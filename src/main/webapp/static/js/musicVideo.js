$(".comment_container .comment_content .good").on("click",function () {
    $(this).on("click",function () {
        let dianzan = this.children[0].children[0];
        let suc = this.children[0].children[1];
        console.log(dianzan.style.color);
        console.log(suc);
        if(suc.style.display === 'none'){
            alert(2);
            $(dianzan).hide(300);
            $(suc).show(300);
        }else {
            $(suc).hide(300);
            $(dianzan).show(300);
        }
    });
});