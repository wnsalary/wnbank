<%@page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="cn.com.infostrategy.to.common.DESKeyTool"%>
<%
String directurl="http://10.4.5.150:8001/cntp/login";
String accountId="";
String usercode=request.getParameter("code");
String url=directurl+"?logintype=single&usercode="+usercode;
if(accountId!=null){
 response.sendRedirect(url);
}

%>