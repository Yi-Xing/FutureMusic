<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2019/5/19
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<html>
<head>
    <title>蒋靓峣</title>

    <style type="text/css">
        #keyword{
            border:2px solid white;
            border-radius: 2px 0 0 2px;
            height:30px;
        }
    </style>
</head>
<body>
<div style="padding:30px 30%">
    <h1>搜索显示</h1>
    <div class="input-group">
        <input type="text" id="keyword">
        <span class="input-group-addon" id="dosearch">搜索</span><!--href="SearchVideoByTitle?title="  -->
    </div>
    <table id ="content_table">
        <!--动态查询出来的数据显示再这个地方  -->
        <tr onclick="copyText()">
            <th id="th0"></th>
        </tr>
        <tr onclick="copyText()">
            <th id="th1"></th>
        </tr>
        <tr onclick="copyText()">
            <th id="th2"></th>
        </tr>
        <tr onclick="copyText()">
            <th id="th3"></th>
        </tr >
        <tr onclick="copyText()">
            <th id="th4"></th>
        </tr>
    </table>

    <h1>springMVC字节流输入上传文件</h1>
    <form name="userForm1" action="/springMVC7/file/upload" enctype="multipart/form-data" method="post">
        <div id="newUpload1">
            <input type="file" name="file">
        </div>

        <input type="button" id="btn_add1" value="增加一行" >
        <input type="submit" value="上传" >
    </form>
    <br>
</div>
</body>
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script>

    $(document).ready(function(){
        $("#keyword").keyup(function(){
            var keyword = $("#keyword").val();
            $.ajax({
                url:"searchRecord",
                data:{keyword:keyword},
                type:'post',
                //请求数据
                //发给后端 异步处理不同于post
                dataType:'json',
                //data返回值// 服务器的状态
                success:function(data){
                    var size=data.length;
                    if(size>5)
                        size=5;
                    for(var i=0;i<size;i++){
                        var content = data[i];
                        var id="th"+i;
                        document.getElementById(id).innerHTML=content;
                    }
                }
            })
        })
    })
    //点击搜索
    $(document).ready(function(){
        $("span").click(function(){
            var title = document.getElementById("keyword").value;
            if(title=="")
                return;
            window.location.href="SearchVideoByTitle?title="+title;
        });
    });
</script>
</html>