//搜索的音乐篇
//搜索狂智能提示篇
    //点击文本框，提示历史搜索记录
        $("#keyword").click(function(){
            $.ajax({
                url:"searchMyRecord",
                type:'post',
                dataType:'json',
                success:function(data,status){
                    //返回data

                }
            })
        })

    // 点击搜索后，将搜索的值加到历史记录里
    //这个跳转不应该用ajax的，应该用model直接跳转
        $("#keyword").keyup(function(){
            var keyword = $("#keyword").val();
            $.ajax({
                url:"addSearchRecord",
                data:{keyword:keyword},
                type:'post',
                dataType:'json',
                success:function(data,status){
                    ///成功后再转到搜索音乐的界面
                    window.location.href="searchListMusic?keyword="+keyword;
                }
            })
        })
    //点击删除，删除历史搜索记录cookie
        $("#keyword").click(function(){
            $.ajax({
                url:"deleteSearchRecord",
                type:'post',
                dataType:'json',
                success:function(data,status){
                    //返回状态，成功
                }
            })
        })

    //根据关键字提示搜索到的音乐、专辑、歌手等
    $(document).ready(function(){
        $("#keyword").keyup(function(){
            var keyword = $("#keyword").val();
            $.ajax({
                url:"searchListAll",
                data:{keyword:keyword},
                type:'post',
                dataType:'json',
                success:function(data,status){
                    //返回data
                }
            })
        })
    })

    //分类搜索歌单




