<%@ page contentType="text/html; charset=gb2312"%>
<html>
<title>ˢ��Applet�������˻���</title>
<body>
<center>
<a href="stop.jsp?stop=Y"><font size=2>ֹͣ</font></a>&nbsp;&nbsp;&nbsp;<a href="stop.jsp?restart=Y"><font size=2>����</font></a><br><br>
<%
if(request.getParameter("stop")!=null && request.getParameter("stop").equals("Y"))
{
System.out.println("Stop Sever From Web....");  //
System.exit(0);
}
else if(request.getParameter("restart")!=null && request.getParameter("restart").equals("Y"))
{
System.out.println("Restart Sever From Web....");  //
Runtime.getRuntime().exec("D:\\TomCat5.5.23\\MyStartup.cmd");  //
System.exit(0);
}
else
{
out.println("</center></body></html>");
}
%>
