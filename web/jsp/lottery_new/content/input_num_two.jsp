<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%  
String zhu = (String)request.getAttribute("zhu");
String num1 = (String)request.getAttribute("num1");
%>
请从下面每组麻将牌组中选择一个，点击选择:<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="1" />
<postfield name="cmd" value="n12" />
</go>
一条|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="2" />
<postfield name="cmd" value="n12" />
</go>
二条|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="3" />
<postfield name="cmd" value="n12" />
</go>
三条
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="4" />
<postfield name="cmd" value="n12" />
</go>
四条|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="5" />
<postfield name="cmd" value="n12" />
</go>
五条|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="6" />
<postfield name="cmd" value="n12" />
</go>
六条
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="7" />
<postfield name="cmd" value="n12" />
</go>
七条|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="8" />
<postfield name="cmd" value="n12" />
</go>
八条|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="9" />
<postfield name="cmd" value="n12" />
</go>
九条
</anchor>
<br/>
<anchor>
<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do?cmd=n1")%>"></go>
返回竞猜
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>