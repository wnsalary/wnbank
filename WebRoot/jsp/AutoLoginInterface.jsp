<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>正在跳转请等待</title>
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
   <form id="loginInfo"  method="post" action="<%=request.getAttribute("url")%>">
	<script language="JavaScript">
		setTimeout("loginInfo.submit();",1000);
	</script>
	<input type="hidden" name="url" value="<%=request.getAttribute("url")%>"/>
	<input type="hidden" name="NQUser" value="<%=request.getAttribute("usercode")%>"/>
	<input type="hidden" name="NQPassword" value="<%=request.getAttribute("pwd")%>" />
</form>
  </body>
</html>
