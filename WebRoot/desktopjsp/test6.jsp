<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test2.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <script src="./js/echarts.js"></script>
  </head>
  
  <body>
  <div id="main" style="width: 400px;height:240px;"></div>
    <script type="text/javascript">
     // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

var axisData = ['周一','周二','周三','周四','周五','周六','周日'];
var data = axisData.map(function (item, i) {
    return Math.round(Math.random() * 1000 * (i + 1));
});
var links = data.map(function (item, i) {
    return {
        source: i,
        target: i + 1
    };
});
links.pop();
option = {
    title: {
        text: '笛卡尔坐标系上的 Graph'
    },
    tooltip: {},
    xAxis: {
        type : 'category',
        boundaryGap : false,
        data : axisData
    },
    yAxis: {
        type : 'value'
    },
    series: [
        {
            type: 'graph',
            layout: 'none',
            coordinateSystem: 'cartesian2d',
            symbolSize: 40,
            label: {
                normal: {
                    show: true
                }
            },
            edgeSymbol: ['circle', 'arrow'],
            edgeSymbolSize: [4, 10],
            data: data,
            links: links,
            lineStyle: {
                normal: {
                    color: '#2f4554'
                }
            }
        }
    ]
};


        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
  </body>
</html>
