<%@ page contentType="text/html; charset=gb2312"%>
<html>
<title>ˢ��Applet�������˻���</title>
<body>
<center>
<a href="reload.jsp?reload=Y"><font size=2>ˢ��</font></a><br><br>
<%
out.println("<font size=2>ԭ�汾��:" + System.getProperty("LAST_STARTTIME") + "</font>");
out.println("<br><br>");
if(request.getParameter("reload")!=null && request.getParameter("reload").equals("Y"))
{
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
String str_date = sdf.format(new java.util.Date());
System.setProperty("LAST_STARTTIME",str_date);
out.println("<font size=2>�°汾��:" + System.getProperty("LAST_STARTTIME") + "</font>");
}
%>
<br><br>
<a href="./index.html"><font size=2> ��  �� </font></a>
</center>
</body>
</html>

