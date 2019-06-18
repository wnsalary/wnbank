<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
StringBuffer action=new StringBuffer("http://10.4.1.68:9704/analytics/saw.dll?PortalPages&PortalPath=%2Fshared%2FTPRISK%2FDashboard%2F%E6%8C%87%E6%A0%87%E4%B8%8B%E9%92%BB%2F%E6%8C%87%E6%A0%87%E4%B8%8B%E9%92%BB");
action.append("&page=").append(request.getAttribute("page")).append("&Action=Navigate").append("&P0=1").append("&P1=eq").append("&P2=%22Dim%20A%20Dim%20Date%22.%22%E6%8A%A5%E5%91%8A%E6%9C%9F%22");
action.append("&P3=").append(request.getAttribute("P3"));
action.append("&NQUser=RISK_TPJT_U&NQPassword=C5909E278F30689F9100D41B7528D5DA");
System.out.println("url============"+action.toString());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>指标查看</title>
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
  正在跳转请等待...
   <form id="loginInfo"  method="post" action="<%=action%>">
	<script language="JavaScript">
		setTimeout("loginInfo.submit();",1000);
	</script>

</form>
  </body>
</html>