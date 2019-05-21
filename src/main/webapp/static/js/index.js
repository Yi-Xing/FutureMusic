$(document).ready(function () {
    //音乐人切换
    $("#EM").hide();$("#RH").hide();
    $("#CN_tab").click(function () {
       $("#CN").show();$("#EM").hide();$("#RH").hide();
    });
    $("#EM_tab").click(function () {
        $("#EM").show();$("#CN").hide();$("#RH").hide();
    });
    $("#RH_tab").click(function () {
        $("#RH").show();$("#CN").hide();$("#EM").hide();
    });

    //排行榜动画
    $(".Ranking_container .container").hover(function () {
        $(this).hover(function () {
            let str = this.children[2].children[0];
            let yinbo = this.children[2].children[1];
            $(str).hide(300);
            $(yinbo).show(300);
        },function () {
            let str = this.children[2].children[0];
            let yinbo = this.children[2].children[1];
            $(yinbo).hide(300);
            $(str).show(300);
        });
    });

    //轮播图箭头
    $(".LB").hover(function () {
        $(this).hover(function () {
            let con = this.children[0].children[2];
            let cons = this.children[0].children[3];
            $(con).show(300);
            $(cons).show(300);
        },function () {
            let con = this.children[0].children[2];
            let cons = this.children[0].children[3];
            $(con).hide(300);
            $(cons).hide(300);
        })
    });
});

$('.icon-like').on('click',function () {
    $(this).css('color','#e52222');
});


//头部
window.onload=function () {
    var mh_a1=document.querySelectorAll(".modal_header a")[0];
    var mh_a2=document.querySelectorAll(".modal_header a")[1];
    var login=document.getElementsByClassName("login")[0];
    var registered=document.getElementsByClassName("registered")[0];
    mh_a1.onclick=function () {
        login.style.display="block";
        registered.style.display="none";
    }
    mh_a2.onclick=function () {
        login.style.display="none";
        registered.style.display="block";
    }
    var httpurl = ""//请求路径



    //文本框默认提示文字
    function textFocus(el) {
        if (el.defaultValue == el.value) { el.value = ''; el.style.color = '#333'; }
    }
    function textBlur(el) {
        if (el.value == '') { el.value = el.defaultValue; el.style.color = '#999'; }
    }

    $(function(){
        //注册页面的提示文字
        (function register(){
            //手机号栏失去焦点
            $(".reg-box .phone").blur(function(){
                reg=/^1[3|4|5|7|8][0-9]\d{4,8}$/i;//验证手机正则(输入前7位至11位)

                if( $(this).val()==""|| $(this).val()=="请输入您的手机号")
                {
                    $(this).addClass("errorC");
                    $(this).next().html("请输入您的手机号");
                    $(this).next().css("display","block");
                }
                else if($(this).val().length<11)
                {
                    $(this).addClass("errorC");
                    $(this).next().html("手机号长度有误！");
                    $(this).next().css("display","block");
                }
                else if(!reg.test($(".reg-box .phone").val()))
                {
                    $(this).addClass("errorC");
                    $(this).next().html("手机号不存在!");
                    $(this).next().css("display","block");
                }
                else
                {
                    $(this).addClass("checkedN");
                    $(this).removeClass("errorC");
                    $(this).next().empty();
                }
            });

            //验证码栏失去焦点
            $(".reg-box .phonekey").blur(function(){
                reg=/^[A-Za-z0-9]{6}$/;
                if( $(this).val()=="" || $(this).val()=="请输入收到的验证码")
                {
                    $(this).addClass("errorC");
                    $(this).next().next().html("请填写验证码");
                    $(this).next().next().css("display","block");
                }
                else if(!reg.test($(".phonekey").val()))
                {
                    $(this).addClass("errorC");
                    $(this).next().next().html("验证码输入有误！");
                    $(this).next().next().css("display","block");
                }
                else
                {
                    $(this).removeClass("errorC");
                    $(this).next().next().empty();
                    $(this).addClass("checkedN");
                }
            });

            //密码栏失去焦点
            $(".reg-box .password").blur(function(){
                reg=/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,22}$/;
                if(!reg.test($(".password").val()))
                {
                    $(this).addClass("errorC");
                    $(this).next().html("格式有误，请输入6~12位的数字、字母或特殊字符！");
                    $(this).next().css("display","block");
                }
                else
                {
                    $(this).removeClass("errorC");
                    $(".reg-box .error3").empty();
                    $(this).addClass("checkedN");
                }
            });
            /*确认密码失去焦点*/
            $(".reg-box .email").blur(function(){
                var pwd1=$('.reg-box input.password').val();
                var pwd2=$(this).val();
                if(($(this).val() == '请再次输入密码' || $(this).val() == "") && (pwd1 == "请输入密码" || pwd1 == "") ){
                    return;
                }else if(pwd1!=pwd2)
                {
                    $(this).addClass("errorC");
                    $(this).removeClass("checkedN");
                    $(this).next().html("两次密码输入不一致！");

                    $(this).next().css("display","block");
                }
                else
                {
                    $(this).removeClass("errorC");
                    $(this).next().empty();
                    $(this).addClass("checkedN");
                }
            });
        })();
        /*生成验证码*/
        (function create_code(){
            function shuffle(){
                var arr=['1','r','Q','4','S','6','w','u','D','I','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p',
                    'q','2','s','t','8','v','7','x','y','z','A','B','C','9','E','F','G','H','0','J','K','L','M','N','O','P','3','R',
                    '5','T','U','V','W','X','Y','Z'];
                return arr.sort(function(){
                    return (Math.random()-.5);
                });
            };
            shuffle();
            function show_code(){
                var ar1='';
                var code=shuffle();
                for(var i=0;i<6;i++){
                    ar1+=code[i];
                };
                //var ar=ar1.join('');
                $(".reg-box .phoKey").text(ar1);
            };
            show_code();
            $(".reg-box .phoKey").click(function(){
                show_code();
            });
        })();

        //登录页面的提示文字
        //账户输入框失去焦点
        (function login_validate(){
            $(".reg-box .account").blur(function(){
                reg=/^1[3|4|5|8][0-9]\d{4,8}$/i;//验证手机正则(输入前7位至11位)

                if( $(this).val()==""|| $(this).val()=="请输入您的账号")
                {
                    $(this).addClass("errorC");
                    $(this).next().html("账号不能为空！");
                    $(this).next().css("display","block");
                }
                else if($(".reg-box .account").val().length<11)
                {
                    $(this).addClass("errorC");
                    $(this).next().html("账号长度有误！");
                    $(this).next().css("display","block");
                }
                else if(!reg.test($(".reg-box .account").val()))
                {
                    $(this).addClass("errorC");
                    $(this).next().html("账号不存在!");
                    $(this).next().css("display","block");
                }
                else
                {
                    $(this).addClass("checkedN");
                    $(this).removeClass("errorC");
                    $(this).next().empty();
                }
            });
            /*密码输入框失去焦点*/
            $(".reg-box .admin_pwd").blur(function(){
                reg=/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,22}$/;

                if($(this).val() == "请输入密码"){
                    $(this).addClass("errorC");
                    $(this).next().html("密码不能为空！");
                    $(this).next().css("display","block");

                }else if(!reg.test($(".admin_pwd").val()))
                {
                    $(this).addClass("errorC");
                    $(this).next().html("密码为6~12位的数字、字母或特殊字符！");
                    $(this).next().css("display","block");
                }else
                {
                    $(this).addClass("checkedN");
                    $(this).removeClass("errorC");
                    $(this).next().empty();
                }
            });

            /*验证码输入框失去焦点*/
            $(".reg-box .photokey").blur(function(){
                var code1=$('.reg-box input.photokey').val().toLowerCase();
                var code2=$(".reg-box .phoKey").text().toLowerCase();
                if(code1!=code2)
                {
                    $(this).addClass("errorC");
                    $(this).next().next().html("验证码输入错误！");
                    $(this).next().next().css("display","block");
                }
                else
                {
                    $(this).removeClass("errorC");
                    $(this).next().next().empty();
                    $(this).addClass("checkedN");
                }
            })
        })();
    });

    /*清除提示信息*/
    function emptyRegister(){
        $(".reg-box .phone,.reg-box .phonekey,.reg-box .password,.reg-box .email").removeClass("errorC");;
        $(".reg-box .error1,.reg-box .error2,.reg-box .error3,.reg-box .error4").empty();
    }
    function emptyLogin(){
        $(".reg-box .account,.reg-box .admin_pwd,.reg-box .photokey").removeClass("errorC");;
        $(".reg-box .error5,.reg-box .error6,.reg-box .error7").empty();
    }

};