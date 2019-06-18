<%@ page contentType="text/html; charset=gb2312"%>
<html>
<title>刷新Applet服务器端缓存</title>
<body>
<center>
<a href="reload.jsp?reload=Y"><font size=2>刷新</font></a><br><br>
<%
out.println("<font size=2>原版本号:" + System.getProperty("LAST_STARTTIME") + "</font>");
out.println("<br><br>");
if(request.getParameter("reload")!=null && request.getParameter("reload").equals("Y"))
{
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
String str_date = sdf.format(new java.util.Date());
System.setProperty("LAST_STARTTIME",str_date);
out.println("<font size=2>新版本号:" + System.getProperty("LAST_STARTTIME") + "</font>");
}
%>
<br><br>
<a href="./index.html"><font size=2> 返  回 </font></a>
</center>
</body>
</html>

