<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="mall" title="<bean:message key="gamename"/>">
<p>
<%
HashMap<Integer, String> map = (HashMap<Integer, String>)request.getAttribute("outmap");
String num = request.getAttribute("num").toString();
String num_add = request.getAttribute("num_add").toString();
String display = (String)request.getAttribute("hint");
if(display!=null&&!display.equals("")&&!display.equals("null")){
%><%=display %><%
}
%>
刮开以下道具条可以获得各种奖励<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/scratchticket.do?cmd=addnum")%>">
</go>
使用【神算符】
</anchor>
剩余次数:<%=num %>【神算符】剩余使用次数<%=num_add %>
<br/>
<%
if(!num.equals("0")){
if( map!=null && map.size()!=0 )
	{
	for(int i = 1;i<11;i++){
	if(map.get(i).equals("*神*秘*宝*物*")){
	%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/scratchticket.do")%>">
<postfield name="cmd" value="n2"/>
<postfield name="num" value="<%=i%>"/>
</go>
<%=map.get(i) %>
</anchor>
<br/>
	<%
	}else{
	%>
	<%=map.get(i) %><br/>
	<%
	}
	}
	}
	}
	else{
	for(int i = 1;i<11;i++){
	%>
	<%=map.get(i) %><br/>
	<%
	}
	}
if(num.equals("0")){
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/scratchticket.do?cmd=over")%>">
</go>
领取奖励
</anchor>
<%
}	
%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>