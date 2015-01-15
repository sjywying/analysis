<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
<a href='<%=path %>/activestat/index'>出货信息</a>
<a href='<%=path %>/dau/index'>日活信息</a>
</body>
</html>
