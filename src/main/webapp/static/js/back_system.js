window.onload=function () {
    function tt(dd) {
        // alert(dd);
    }

    var GG = {
        "kk": function (mm) {
            // alert(mm);
        }
    }
    $("#page").initPage(200, 1, GG.kk);
    // listcount是数据总条数
    //模态框
    $("#bookmarak").click(function () {
        $(".side").toggle();
    });
    var hid_show=document.getElementsByClassName("hid_show");
    var showDiv=document.getElementById("showDiv");

    function overShow(obj,e) {
        var showDiv = document.getElementById('showDiv');
        var theEvent = window.event|| e;
        showDiv.style.left = theEvent.clientX+"px";
        showDiv.style.top = theEvent.clientY+"px";
        showDiv.style.display = 'block';
        //alert(obj.innerHTML);
        showDiv.innerHTML = obj.innerHTML;
    }
    overShow("hid_show","overShow")

    console.log(hid_show);
    console.log(showDiv);
    function outHide() {
        var showDiv = document.getElementById('showDiv');
        showDiv.style.display = 'none';
        showDiv.innerHTML = '';
    }

    // var re = document.getElementById("re");
    // var mol = document.getElementsByClassName("mol")[0];
    // var replace = document.getElementsByTagName("span");














}