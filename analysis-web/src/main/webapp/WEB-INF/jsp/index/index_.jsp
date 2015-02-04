<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
   <title>统计</title>
   <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
   <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
   <style type="text/css">
         .leftMenu{
            width: 10%;
            height: 168px;
            margin-top: 20px;
         }
         .rightContent{
            margin-left: 10px;
            width: 88%;
            height: 700px;
            float: right;
            margin-top: -148px;
         }
         .iframeCopntent{
            width: 100%;
            height: 700px;
         }
   </style>
</head>
<body>
<div class="leftMenu">
   <ul class="nav nav-pills nav-stacked">
   <li class="active"><a>出货信息</a></li>
   <li class=""><a href="#">日活信息</a></li>
   <li><a href="/sm/logout">退出</a></li>
</ul>
</div>
<div class="rightContent">
   <iframe  style="display:block;"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" class="iframeCopntent" src="<%=path %>/activestat/index"></iframe>
</div>
<script type="text/javascript">
    $(".leftMenu").find("li").click(function(){
         $(this).addClass("active").siblings().removeClass("active");
         var menue = $(this).find("a").text();
         var url = "";
         if(menue=="出货信息"){
            url="<%=path %>/activestat/index";
         }else if(menue=="日活信息"){
            url="<%=path %>/dau/index";
         }
         $(".iframeCopntent").attr("src",url);
      });
</script>
</body>
</html>	