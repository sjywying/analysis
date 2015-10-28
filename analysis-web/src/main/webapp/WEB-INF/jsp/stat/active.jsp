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

        <script src="../static/highstock/highstock.js"></script>
        <script src="../static/highstock/modules/exporting.js"></script>

		<script type="text/javascript">
$(function () {

	/**
	 * Sand-Signika theme for Highcharts JS
	 * @author Torstein Honsi
	 */

	Highcharts.setOptions({ global: { useUTC: false } });

// Load the fonts
			Highcharts.createElement('link', {
				href: '//fonts.googleapis.com/css?family=Signika:400,700',
				rel: 'stylesheet',
				type: 'text/css'
			}, null, document.getElementsByTagName('head')[0]);

// Add the background image to the container
	Highcharts.wrap(Highcharts.Chart.prototype, 'getContainer', function (proceed) {
		proceed.call(this);
		this.container.style.background = 'url(http://www.highcharts.com/samples/graphics/sand.png)';
	});


	Highcharts.theme = {
		colors: ["#f45b5b", "#8085e9", "#8d4654", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
			"#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
		chart: {
			backgroundColor: null,
			style: {
				fontFamily: "Signika, serif"
			}
		},
		title: {
			style: {
				color: 'black',
				fontSize: '16px',
				fontWeight: 'bold'
			}
		},
		subtitle: {
			style: {
				color: 'black'
			}
		},
		tooltip: {
			borderWidth: 0
		},
		legend: {
			itemStyle: {
				fontWeight: 'bold',
				fontSize: '13px'
			}
		},
		xAxis: {
			labels: {
				style: {
					color: '#6e6e70'
				}
			}
		},
		yAxis: {
			labels: {
				style: {
					color: '#6e6e70'
				}
			}
		},
		plotOptions: {
			series: {
				shadow: true
			},
			candlestick: {
				lineColor: '#404048'
			},
			map: {
				shadow: false
			}
		},

		// Highstock specific
		navigator: {
			xAxis: {
				gridLineColor: '#D0D0D8'
			}
		},
		rangeSelector: {
			buttonTheme: {
				fill: 'white',
				stroke: '#C0C0C8',
				'stroke-width': 1,
				states: {
					select: {
						fill: '#D0D0D8'
					}
				}
			}
		},
		scrollbar: {
			trackBorderColor: '#C0C0C8'
		},

		// General
		background2: '#E0E0E8'

	};

// Apply the theme
	Highcharts.setOptions(Highcharts.theme);

	
	var options = {
        rangeSelector : {
            selected : 1
        },

        title : {
            text : '出货数据'
        },

        series : [{
            name : '出货',
//            data : data,
            tooltip: {
                valueDecimals: 0
            }
        }//,
//			{
//			name : '注册',
////            data : data,
//			tooltip: {
//				valueDecimals: 0
//			}
//		}
		]
    };
	
	$.ajax({
		type : 'post',
		url : 'getAllByChannel.json',
		contentType : 'applications/json',
		processData : true,
		dataType: 'json',
		success : function(data) {

            options.series[0].data = data.result;
//			options.series[1].data = data.reg;

            $("#container").highcharts("StockChart", options);
			
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
