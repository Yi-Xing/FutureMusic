window.onload=function () {
    $(function(){
        $("#city_1").citySelect();
        $("#city_2").citySelect({
            prov:"北京",
            nodata:"none"
        });
    });


}