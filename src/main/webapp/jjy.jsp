<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2019/5/19
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>jjy</title>

    <style type="text/css">

        input{
            width:500px;
            height:28px;
            outline:none;
        }

        #keyword{
            border:2px solid white;
            border-radius: 2px 0 0 2px;
            height:30px;
            background-color: #1b6d85;
        }
    </style>
</head>
<body>
<div style="padding:30px 30%">
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
    </table>

</div>
</body>
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script>
    //keyup提示相关记录
    $(document).ready(function(){
        $("#keyword").keyup(function(){
            var keyword = $("#keyword").val();
            $.ajax({
                url:"searchListMusic",
                data:{keyword:keyword},
                type:'post',
                dataType:'json',
                success:function(data){
                    alert(data);
                /*    var size=data.length;
                    if(size>3)
                        size=3;
                    for(var i=0;i<size;i++){
                        var content = data[i];
                        var id="th"+i;
                        document.getElementById(id).innerHTML=content;
                    }*/
                }
            })
        })
    })
    //点击搜索加到搜索记录里
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