<%@ page contentType="text/html; charset=gb2312"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.HttpURLConnection"%>

<!-- 该jsp是可以不要密码,直接访问Weblight,用于与第三方portal集成时使用.即从第三方web访问该jsp就可以直接调用Weblight相关接口 -->
<%
	//取得所有参数
	String str_fnname = request.getParameter("fnname");
	URL url = new URL("http://127.0.0.1:9001/WebCallServlet?fnname=" + str_fnname); //访问直接的Servlet,该Servlet是必须判断是来自本机的调用才是安全的!
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	String str_returnmsg = conn.getResponseMessage();
	out.println(str_returnmsg); //输出
%>

