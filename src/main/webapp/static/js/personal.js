//模态框
$('#myModal').on('shown.bs.modal', function () {
    $('#myInput').focus()
});
// $('#Recharge_click').modal($("#recharge_container"));
//锁定个人中心
var personalLock = 0;
$(".color_2 nav .navbar-right .lock").on('click',function () {
    var onclock = $(this);
    if(personalLock === 0){
        $(onclock).addClass('green');
        personalLock = 1;
        alert('个人中心已锁定！');
    }else {
        $(onclock).removeClass('green');
        personalLock = 0;
        alert('个人中心已解锁！')
    }
});
//个人中心TAB切换栏
$(".collapse .navbar-nav .personal_tab").click(function () {
    var personalCenterTab = $(".personalCenterTab");
    console.log($(this)[0]);
    var tmp = $(this)[0];
    if($(this).className === 'active'){
        let num = $(tmp).index();
        for (i = 0; i < personalCenterTab.length; i++) {
            let obj = personalCenterTab.eq(i);
            console.log(obj);
            if(i === num) {
                $(obj).show();
            }else{
                $(obj).hide();
            }
        }
    }else {
        $(tmp).addClass('active');
        let num = $(tmp).index();
        for(j = 0 ; j < $(this).parents()[0].children.length ; j++){
            console.log($(tmp).index());
            if (j === num){
                $(this).parents()[0].children[j].classList.add('active');
            }
            else {
                console.log($(this).parents()[0].children[j]);
                $(this).parents()[0].children[j].classList.remove('active');
            }
        }
        for (i = 0; i < personalCenterTab.length; i++) {
            let obj = personalCenterTab.eq(i);
            if(i === num) {
                $(obj).show();
            }else{
                $(obj).hide();
            }
        }
    }
});
//修改个人信息表单
$("#changeInformation").click(function () {
    $("#myInformation form").slideToggle(300);
});

//我喜欢tab切换
$(".btn-group button").hover(function () {
    $(this).click(function () {
        let num = $(this).index();
        console.log($(this).index());
        let likeTab = $(".likeTab");
        for (i = 0; i < likeTab.length; i++) {
            if(i === num){
                $(likeTab[i]).slideDown(200);
            }else {
                $(likeTab[i]).slideUp(200);
            }
        }
    })
});


// ajax


