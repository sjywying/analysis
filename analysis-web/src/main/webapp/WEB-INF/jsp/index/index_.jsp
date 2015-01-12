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
<a href='<%=path %>/manuActiveStat/index'>出货信息</a>
<a href='<%=path %>/dau/index'>出货信息</a>
</body>
</html>
