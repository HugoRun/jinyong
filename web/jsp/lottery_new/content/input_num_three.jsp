<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%  
String zhu = (String)request.getAttribute("zhu");
String num1 = (String)request.getAttribute("num1");
String num2 = (String)request.getAttribute("num2");
%>
请从下面每组麻将牌组中选择一个，点击选择:<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="1" />
<postfield name="cmd" value="n13" />
</go>
一萬|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="2" />
<postfield name="cmd" value="n13" />
</go>
二萬|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="3" />
<postfield name="cmd" value="n13" />
</go>
三萬
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="4" />
<postfield name="cmd" value="n13" />
</go>
四萬|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="5" />
<postfield name="cmd" value="n13" />
</go>
五萬|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="6" />
<postfield name="cmd" value="n13" />
</go>
六萬
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="7" />
<postfield name="cmd" value="n13" />
</go>
七萬|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="8" />
<postfield name="cmd" value="n13" />
</go>
八萬|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="9" />
<postfield name="cmd" value="n13" />
</go>
九萬
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