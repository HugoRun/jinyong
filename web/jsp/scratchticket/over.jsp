<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="mall" title="<s:message key = "gamename"/>">
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
剩余次数:<%=num %>【神算符】剩余使用次数<%=num_add %><br/>
本卡包含以下宝物:
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
%>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1&amp;w_type=6")%>" method="get"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>