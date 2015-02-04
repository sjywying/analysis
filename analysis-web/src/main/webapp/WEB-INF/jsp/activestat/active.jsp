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
		<script src="../static/highcharts/modules/drilldown.js"></script>

		<script type="text/javascript">
$(function () {
	
	var options = {
        chart: {
            renderTo: 'container',
            type: 'column'
        },
        title: {
            text: '各月份出货统计'
        },
        subtitle: {
            //text: '统计标准：注册后不再同一天内的第三天算作激活。'
            text: ''
        },
        xAxis: {
            type: 'category'
        },
        yAxis: {
            title: {
                text: '出货数量'
            }
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y}'
                }
            }
        },
        tooltip: {
        	//headerFormat: '<span style="color:{point.color}">{point.name}出货</span>: <b>{point.y}</b><br/>'
            //headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            //pointFormat: '<span style="color:{point.color}">{point.name}出货</span>: <b>{point.y}</b> of total<br/>'
        },
        series: [{
        	name: '出货',
            colorByPoint: true
        }],
        drilldown:{}
    };
	
	$.ajax({
		type : 'post',
		url : 'getAllByChannel.json',
		contentType : 'applications/json',
		processData : true,
		//data : {
		//	'channel':'100322'
		//},
		dataType: 'json',
		success : function(data) {
			
            var brands = {},
            brandsData = [],
            models = {},
            drilldownSeries = [];
			
			var columnarr = data.actives;
			$.each(columnarr, function (i, line) {
				var brand = line.yyyymm;
				var num = line.num;
				var model = line.model;
				
				if (!brands[brand]) {
                    brands[brand] = num;
                } else {
                    brands[brand] += num;
                }
				
				 if (model !== null) {
                    if (!models[brand]) {
                    	 models[brand] = [];
                     }
                     models[brand].push([model, num]);
                 }
			});
			
			$.each(brands, function (yyyymm, num) {
                brandsData.push({
                    name: yyyymm,
                    y: num,
                    drilldown: models[yyyymm] ? yyyymm : null
                });
            });
            $.each(models, function (model, num) {
                drilldownSeries.push({
                    name: model,
                    id: model,
                    data: num
                });
            });
			
			options.series[0].data = brandsData;
			options.drilldown.series = drilldownSeries;
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
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>
