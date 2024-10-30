<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
<%  
String zhu = (String)request.getAttribute("zhu");
%>
请从下面每组麻将牌组中选择一个，点击选择:<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="1" />
<postfield name="cmd" value="n11" />
</go>
一筒|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="2" />
<postfield name="cmd" value="n11" />
</go>
二筒|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="3" />
<postfield name="cmd" value="n11" />
</go>
三筒
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="4" />
<postfield name="cmd" value="n11" />
</go>
四筒|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="5" />
<postfield name="cmd" value="n11" />
</go>
五筒|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="6" />
<postfield name="cmd" value="n11" />
</go>
六筒
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="7" />
<postfield name="cmd" value="n11" />
</go>
七筒|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="8" />
<postfield name="cmd" value="n11" />
</go>
八筒|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="9" />
<postfield name="cmd" value="n11" />
</go>
九筒
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