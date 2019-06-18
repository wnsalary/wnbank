<%@page contentType="application/json;charset=utf-8"  language="java" pageEncoding="UTF-8"%>
<%@page import="cn.com.infostrategy.bs.common.CommDMO"%>
<%@page import="org.json.JSONObject" %>
<%@page import="java.util.*"%>
<jsp:directive.page import="cn.com.infostrategy.to.common.HashVO"/>
<%
//该jsp主要是生成门户网站工作台的已办显示
String path=request.getContextPath();
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("Pragma", "no-cache");//http 1.0
response.setHeader("Cache-Control", "no-cache");//http 1.1
response.setDateHeader("Expires", 0); 
Cookie[] cookies = request.getCookies();
System.out.println("have_read_json.jsp中 cookies=====" + cookies);
String uuid="";//统一身份认证的UUID
String userid="";//风控系统的用户id
String code="";//登录用户名
String lastTitle="";  //最后一个标题
for (int i = 0; i < cookies.length; i++) {
	Cookie cook = cookies[i];
	if("CNTPAuth".equals(cook.getName())){
		uuid=cook.getValue();
	}
}
System.out.println("have_read_json.jsp中 uuid==="+uuid);
CommDMO dmo= new CommDMO();
if(null!=uuid&&!uuid.equals("")){
	HashVO voss[]=dmo.getHashVoArrayByDS(null,"select id,code from pub_user where uuid='"+uuid+"'");
	if(voss!=null&&voss.length>0){
		userid=voss[0].getStringValue("id");
		code=voss[0].getStringValue("code");
}
}
String site=basePath+"jsp/ssoRedirect.jsp?code="+uuid;
System.out.println("have_read_json.jsp中  site==="+site);
%><%
if(userid!=null&&!userid.equals("")){
    String sql = "select templetname, count(*) count from pub_message where msgtype='工作流传阅消息' and  isdelete='N'  and exists (select id from pub_message_readed a where a.msgid = pub_message.id and userid = '"+userid+"') and isdelete='N'  group by templetcode, templetname ;";
    System.out.println(sql);
    HashVO vos[]=dmo.getHashVoArrayByDS(null,sql);
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
    	"type":"已阅", 
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
