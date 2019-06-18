<%@ page language="java" import="java.util.*,java.net.URLDecoder" pageEncoding="UTF-8"%>
<jsp:directive.page import="cn.com.infostrategy.to.common.TBUtil"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String desc=URLDecoder.decode(request.getParameter("desc"),"utf-8");
HashMap <String,String>map=TBUtil.getTBUtil().convertStrToMapByExpress(desc,";",":");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>明细查看</title>
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
  <br>
    <table align="center"  width=400 border="1" cellspacing="0" cellpadding="0">
    <% 
     for (String key : map.keySet()) {
     %>
     <tr >
     <td width="50%"><%=key %> </td><td><%=map.get(key) %></td>
      <tr>
     <%System.out.println("key= "+ key + " and value= " + map.get(key));
  }
    %>
</table>
  </body>
</html>
