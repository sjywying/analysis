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
<!-- 		<script src="../static/highcharts/modules/data.js"></script> -->
		<script src="../static/highcharts/modules/exporting.js"></script>

		<script type="text/javascript">
$(function () {
	
	var options = {
	        chart: {
	        	renderTo: 'container',
	            type: 'column'
	        },
	        title: {
	            text: '各月份出货报表'
	        },
	        subtitle: {
	            text: 'www.winksi.com'
	        },
	        xAxis: {
	            type: 'category',
	            labels: {
	                rotation: -45,
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '出货数量'
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            pointFormat: 'sum {point.y}'
	        },
	        series: [{
	            name: 'num',
	            colorByPoint: true,
	            data: [],
	            dataLabels: {
	                //enabled: true,
	                rotation: -90,
	                //color: '#FFFFFF',
	                align: 'right',
	                x: 4,
	                y: 10,
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif',
	                    textShadow: '0 0 3px black'
	                }
	            }
	        }]
	    }
	
	
	$.ajax({
		type : 'post',
		url : 'getByChannel.json',
		contentType : 'applications/json',
		processData : true,
		dataType: 'json',
		success : function(data) {
			options.series[0].data = data.list;
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
