<%@page contentType="application/json;charset=utf-8"  language="java" pageEncoding="UTF-8"%>
<%@page import="cn.com.infostrategy.bs.common.CommDMO"%>
<%@page import="org.json.JSONObject" %>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<jsp:directive.page import="java.net.URLDecoder"/>
<jsp:directive.page import="cn.com.infostrategy.to.common.HashVO"/>
<%= request.getParameter("callback") %>
<%
String path=request.getContextPath();
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("Pragma", "no-cache");//http 1.0
response.setHeader("Cache-Control", "no-cache");//http 1.1
response.setDateHeader("Expires", 0); 
//setLastAccess
Cookie[] cookies = request.getCookies();
System.out.println("cookies=====" + cookies);
String uuid="";//统一身份认证的UUID
String userid="";//风控系统的用户id
String corpid="";//机构id
String code="";//登录用户名
String []ids_parentiddept=null;
String []ids_roles=null;
StringBuffer sql_unread=new StringBuffer("select '待阅' activity_type, count(*) c1  from pub_message where msgtype='工作流传阅消息' and  isdelete='N'  ");

for (int i = 0; i < cookies.length; i++) {
	Cookie cook = cookies[i];
	if("CNTPAuth".equals(cook.getName())){
		uuid=cook.getValue();
	}
}
System.out.println("uuid==="+uuid);
CommDMO dmo= new CommDMO();
if(null!=uuid&&!uuid.equals("")){
	HashVO voss[]=dmo.getHashVoArrayByDS(null,"select id,code from pub_user where uuid='"+uuid+"'");
	if(voss!=null&&voss.length>0){
		userid=voss[0].getStringValue("id");
		code=voss[0].getStringValue("code");
		sql_unread.append(" and (receiver like '%;" + userid + ";%' ");
		String sql1= "select userdept,isdefault from pub_user_post where userid = '" + userid + "' ";
		HashVO vos_dept[]=dmo.getHashVoArrayByDS(null,sql1);
		if (vos_dept != null && vos_dept.length > 0) {
				corpid = vos_dept[0].getStringValue("userdept");
			}
		}
		System.out.println("corpid==="+corpid);		
		if(corpid!=null&&!corpid.equals("")){
			ids_parentiddept=dmo.getStringArrayFirstColByDS(null,"select id from pub_corp_dept where linkcode like (select linkcode from pub_corp_dept where id='"+corpid+"')||'%'");
		}
		String sql2= "select roleid from pub_user_role where userid = '" + userid + "' ";
		ids_roles=dmo.getStringArrayFirstColByDS(null,sql2);
System.out.println("ids_parentiddept==="+ids_parentiddept);		
		if (ids_parentiddept != null && ids_parentiddept.length > 0) {
			sql_unread.append(" or ( ");
			for (int i = 0; i < ids_parentiddept.length; i++) {
				if (i == 0) {
					sql_unread.append(" recvcorp like '%;" + ids_parentiddept[i] + ";%' ");
				} else {
					sql_unread.append(" or recvcorp like '%;" + ids_parentiddept[i] + ";%' ");
				}
			}
			sql_unread.append(" ) ");
		}
		if (ids_roles != null && ids_roles.length > 0) {
			sql_unread.append(" or ( ");
			for (int i = 0; i < ids_roles.length; i++) {
				if (i == 0) {
					sql_unread.append(" recvrole like '%;" + ids_roles[i] + ";%' ");
				} else {
					sql_unread.append(" or recvrole like '%;" + ids_roles[i] + ";%' ");
				}
			}
			sql_unread.append(" ) ");
		}
		sql_unread.append(" ) ");
		sql_unread.append(" and not exists (select id from pub_message_readed a where a.msgid = pub_message.id and userid = '" + userid + "') ");
	System.out.println("sql_unread==="+sql_unread.toString());		
}
System.out.println("basePath==="+basePath);
System.out.println("url_daibanjson==="+request.getRequestURI().toString());
String site=basePath+"jsp/ssoRedirect.jsp?code="+code;
System.out.println("site==="+site);
%><%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
if(userid!=null&&!userid.equals("")){
    String sql = "select '待办' activity_type, count(*) c1 from pub_task_deal where (dealuser='"+userid+"' or accruserid='"+userid+"') "+ 
			"union all "+
			sql_unread.toString();
    System.out.println(sql);
    HashVO vos[]=dmo.getHashVoArrayByDS(null,sql);
    String todoSize="0";
    String toreadSize="0";
    for(int i=0;i<vos.length;i++){
    	HashVO vo=vos[i];
        if("待办".equals(vo.getStringValue("activity_type"))){
        	todoSize=vo.getStringValue("c1");
        }else if("待阅".equals(vo.getStringValue("activity_type"))){
        	toreadSize=vo.getStringValue("c1");
        }
    }
    if(todoSize!=null&&todoSize.equals("0")&&toreadSize!=null&&toreadSize.equals("0")){//如果都为空的话则不显示
    }else{
%>({
    "success": true, <%-- boolean类型，必需值，true：表示成功；false：表示失败 。--%>
    "message":"", <%-- 字符类型，可以为空，返回请求相关信息，如请求失败的错误信息等。 --%>
    "systemName": "风控系统" ,<%-- 字符类型。 --%>
    "items":[  <%-- 数组：可以为空，返回请求数据集。--%>
    {
    "type":"待办",
    "count": "<%= todoSize %>",   <%-- 数字类型，待办总数。--%>
    "moreUrl" : "<%= site %>"
    },
    {    
    "type":"待阅",
    "count": "<%= toreadSize %>",   <%-- 数字类型，待办总数。--%>
    "moreUrl" : "<%= site %>"
    }
    ]
    })
<%}}else{}
%>
