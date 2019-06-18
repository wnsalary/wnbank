<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
  </head>
  
  <body>
 <div id="main" style="width: 400px;height:240px;"></div>  
     <script src="./js/echarts.js"></script>
     <script src="./js/china.json"></script>
     <script src="./js/china.js"></script>
 <!-- 为ECharts准备一个具备大小（宽高）的Dom -->  
      
    <script type="text/javascript">  
        // 基于准备好的dom，初始化echarts实例  
        var myChart = echarts.init(document.getElementById('main'));  
  
        option = {  
            tooltip: {  
                trigger: 'item',  
                formatter: '{b}'  
            },  
            series: [  
                {  
                    name: '中国',  
                    type: 'map',  
                    mapType: 'china',  
                    selectedMode : 'multiple',  
                    label: {  
                        normal: {  
                            show: true  
                        },  
                        emphasis: {  
                            show: true  
                        }  
                    },  
                    data:[  
                        {name:'广东', selected:true}  
                    ]  
                }  
            ]  
        };  
  
        // 使用刚指定的配置项和数据显示图表。  
        myChart.setOption(option);  
    </script>  
  </body>
</html>
