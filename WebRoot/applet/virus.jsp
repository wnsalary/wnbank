<%@ page contentType="text/html; charset=gb2312"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.HttpURLConnection"%>

<!-- ��jsp�ǿ��Բ�Ҫ����,ֱ�ӷ���Weblight,�����������portal����ʱʹ��.���ӵ�����web���ʸ�jsp�Ϳ���ֱ�ӵ���Weblight��ؽӿ� -->
<%
	//ȡ�����в���
	String str_fnname = request.getParameter("fnname");
	URL url = new URL("http://127.0.0.1:9001/WebCallServlet?fnname=" + str_fnname); //����ֱ�ӵ�Servlet,��Servlet�Ǳ����ж������Ա����ĵ��ò��ǰ�ȫ��!
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	String str_returnmsg = conn.getResponseMessage();
	out.println(str_returnmsg); //���
%>

