$(".saveCenter_tab .saveTab").hover(function () {
    $(this).click(function () {
        let num = $(this).index();
        console.log($(this).index());
        let likeTab = $(".find");
        for (i = 0; i < likeTab.length; i++) {
            if (i === num) {
                $(likeTab[i]).slideDown(200);
            } else {
                $(likeTab[i]).slideUp(200);
            }
        }
        let sss = $('.saveTab');
        for(j = 0 ; j < sss.length ; j++){
            if(j === num){
                $(sss[j]).addClass('active');
            }else {
                $(sss[j]).removeClass('active');
            }
        }
    })
});
