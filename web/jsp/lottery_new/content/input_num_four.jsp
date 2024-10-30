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
String num2 = (String)request.getAttribute("num2");
String num3 = (String)request.getAttribute("num3");
%>
请从下面每组麻将牌组中选择一个，点击选择:<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="<%=num3 %>" />
<postfield name="num4" value="1" />
<postfield name="cmd" value="n2" />
</go>
东风|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="<%=num3 %>" />
<postfield name="num4" value="2" />
<postfield name="cmd" value="n2" />
</go>
南风|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="<%=num3 %>" />
<postfield name="num4" value="3" />
<postfield name="cmd" value="n2" />
</go>
西风|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="<%=num3 %>" />
<postfield name="num4" value="4" />
<postfield name="cmd" value="n2" />
</go>
北风
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="<%=num3 %>" />
<postfield name="num4" value="5" />
<postfield name="cmd" value="n2" />
</go>
红中|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="<%=num3 %>" />
<postfield name="num4" value="6" />
<postfield name="cmd" value="n2" />
</go>
白板|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="<%=zhu %>" />
<postfield name="num1" value="<%=num1 %>" />
<postfield name="num2" value="<%=num2 %>" />
<postfield name="num3" value="<%=num3 %>" />
<postfield name="num4" value="7" />
<postfield name="cmd" value="n2" />
</go>
发财
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