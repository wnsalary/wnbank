<%@page contentType="application/json;charset=utf-8"  language="java" pageEncoding="UTF-8"%>
<%@page import="cn.com.infostrategy.bs.common.CommDMO"%>
<%@page import="org.json.JSONObject" %>
<%@page import="java.util.*"%>
<jsp:directive.page import="cn.com.infostrategy.to.common.HashVO"/>
<%= request.getParameter("callback") %>
<%
String path=request.getContextPath();
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("Pragma", "no-cache");//http 1.0
response.setHeader("Cache-Control", "no-cache");//http 1.1
response.setDateHeader("Expires", 0); 
System.out.println("to_read_json.jsp------");
//setLastAccess
Cookie[] cookies = request.getCookies();
System.out.println("to_read_json.jsp cookies=====" + cookies);
String uuid="";//统一身份认证的UUID
String userid="";//风控系统的用户id
String corpid="";//机构id
String code="";//登录用户名
String lastTitle="";  //最后一个标题
String []ids_parentiddept=null;
String []ids_roles=null;
StringBuffer sql_unread=new StringBuffer("select templetname, count(*) count from pub_message where msgtype='工作流传阅消息' and  isdelete='N'  ");
for (int i = 0; i < cookies.length; i++) {
	Cookie cook = cookies[i];
	if("CNTPAuth".equals(cook.getName())){
		uuid=cook.getValue();
	}
}
System.out.println("to_read_json.jsp uuid==="+uuid);
CommDMO dmo= new CommDMO();
if(null!=uuid&&!uuid.equals("")){
	HashVO voss[]=dmo.getHashVoArrayByDS(null,"select id,code from pub_user where uuid='"+uuid+"'");
	if(voss!=null&&voss.length>0){//下面这段代码主要获取工作流中待阅的sql语句
		userid=voss[0].getStringValue("id");
		code=voss[0].getStringValue("code");
		sql_unread.append(" and (receiver like '%;" + userid + ";%' ");
		String sql1= "select userdept,isdefault from pub_user_post where userid = '" + userid + "' ";
		HashVO vos_dept[]=dmo.getHashVoArrayByDS(null,sql1);
		if (vos_dept != null && vos_dept.length > 0) {
				corpid = vos_dept[0].getStringValue("userdept");
			}
		}
		System.out.println("to_read_json.jsp corpid==="+corpid);		
		if(corpid!=null&&!corpid.equals("")){
			ids_parentiddept=dmo.getStringArrayFirstColByDS(null,"select id from pub_corp_dept where linkcode like (select linkcode from pub_corp_dept where id='"+corpid+"')||'%'");
		}
		String sql2= "select roleid from pub_user_role where userid = '" + userid + "' ";
		ids_roles=dmo.getStringArrayFirstColByDS(null,sql2);
System.out.println("to_read_json.jsp ids_parentiddept==="+ids_parentiddept);		
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
		sql_unread.append(" and not exists (select id from pub_message_readed a where a.msgid = pub_message.id and userid = '" + userid + "') group by templetcode, templetname");
	System.out.println("to_read_json sql==="+sql_unread.toString());		
}
System.out.println("basePath==="+basePath);
System.out.println("url_daibanjson==="+request.getRequestURI().toString());
String site=basePath+"jsp/ssoRedirect.jsp?code="+uuid;
System.out.println("site==="+site);
%><%
if(userid!=null&&!userid.equals("")){
    HashVO vos[]=dmo.getHashVoArrayByDS(null,sql_unread.toString());
    if(vos!=null&&vos.length>0){
    	int count_all=0;//这个是总数量需要在最上面显示
        for(int i1=0;i1<vos.length;i1++){
        HashVO vo=vos[i1];
        int modulecount=vo.getStringValue("count")==null?0:Integer.parseInt(vo.getStringValue("count"));
        count_all+=modulecount;
    	}
    	%>
    	({
	    "success": true, 
    	"message":"", 
    	"systemName": "风控系统" , 
    	"type":"待阅", 
    	"count": "<%= count_all %>",   
    	"moreUrl" : "<%= site %>",
    	"items":[   
	   <%
	   for(int i=0;i<vos.length-1;i++){  //到length-1  再加入最后一个，因为最后一个没有逗号
	    HashVO vo=vos[i];
        String title=vo.getStringValue("templetname")+"("+vo.getStringValue("count")+")";
        %>
         { 
    	"title":"<%= title %>", 
		"url":"<%= site %>",
   		"date":"", 
   		"type":"", 
   		"org":"" 
    	},
	   <%}
	   lastTitle=vos[vos.length-1].getStringValue("templetname")+"("+vos[vos.length-1].getStringValue("count")+")";//取得最后一个，便于拼接最后一个json
	   %>
	   { 
    	"title":"<%= lastTitle %>", 
		"url":"<%= site %>",
   		"date":"", 
   		"type":"", 
   		"org":"" 
    	}
	   
	   ]
     })<%}else{
     %>
     ({
	    "success": true, 
    	"message":"", 
    	"systemName": "风控系统" , 
    	"type":"待阅", 
    	"count": "0",   
    	"moreUrl" : "<%= site %>",
    	"items":[]
    	})
     <%
     }}else{}
%>
