<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig,com.lw.vo.ttbox.TTBOXVO" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="mall" title="<s:message key = "gamename"/>">
<p>
<%
HashMap<Integer, TTBOXVO> map = (HashMap<Integer, TTBOXVO>)request.getAttribute("outmap");
String num = request.getAttribute("num").toString();
String num_add = request.getAttribute("num_add").toString();
String display = (String)request.getAttribute("display");
if(display!=null&&!display.equals("")&&!display.equals("null")){
%><%=display %><br/><%
}
%>
你只能领取以下物品中的一件,使用【金手指】可轮转【聚宝盆】中的宝物<br/>
<%
if( map!=null && map.size()!=0 )
	{
	TTBOXVO vo1 = map.get(0);
	TTBOXVO vo2 = map.get(1);
	TTBOXVO vo3 = map.get(2);
	%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/ttbox.do")%>">
<postfield name="cmd" value="one"/>
</go>
<%=vo1.getGoodname() %>
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/ttbox.do")%>">
<postfield name="cmd" value="two"/>
</go>
<%=vo2.getGoodname() %>
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/ttbox.do")%>">
<postfield name="cmd" value="three"/>
</go>
<%=vo3.getGoodname() %>
</anchor>
<br/>
	<%
	}
%>
请点击你想要的物品领取<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/ttbox.do?cmd=addnum")%>">
</go>
使用【金手指】
</anchor>
<br/>
【金手指】剩余使用次数<%=num_add %>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>