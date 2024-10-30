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
String display = (String)request.getAttribute("display");
%>
<%=display %>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="2" />
<postfield name="cmd" value="n10" />
</go>
2注|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="10" />
<postfield name="cmd" value="n10" />
</go>
10注|
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="100" />
<postfield name="cmd" value="n10" />
</go>
100注
</anchor>
<br/>
<input name="zhu"  type="text" size="3"  maxlength="3" format="5N" />上限999注<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do")%>">
<postfield name="zhu" value="$zhu" />
<postfield name="cmd" value="n10" />
</go>
确认购买
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