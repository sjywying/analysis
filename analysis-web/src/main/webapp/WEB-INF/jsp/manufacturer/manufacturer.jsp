<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>出货统计</title>
		
		<script type="text/javascript" src="../static/js/jquery-1.9.1.js"></script>
		
		<script src="../static/highcharts/highcharts.js"></script>
		<script src="../static/highcharts/modules/exporting.js"></script>

		<script type="text/javascript">
$(function () {
	
	var options = {
		chart: {
        	renderTo: 'container',
            type: 'line'
        },
        title: {
            text: '出货情况统计',
            x: -20 //center
        },
        subtitle: {
            text: '注册，激活',
            x: -20
        },
        xAxis: {
            categories: []
        },
        yAxis: {
            title: {
                text: '出货信息'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: ''
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '注册',
            data: []
        }, {
            name: '激活',
            data: []
        }]
	};
	
	
	$.ajax({
		type : 'post',
		url : 'getByChannel.json',
		contentType : 'applications/json',
		processData : true,
		dataType: 'json',
		success : function(data) {
			options.xAxis.categories = data.cdate;
			options.series[0].data = data.regnum;
			options.series[1].data = data.activenum;
			new Highcharts.Chart(options);
			
		},
		error : function(data) {
			console.log(data);
		}
	});

});


		</script>
	</head>
<body>
<div id="container" style="min-width: 500px; height: 400px; margin: 0 auto"></div>
</body>
</html>