<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.beoffprop.BeOffPropVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		BeOffPropVO vo = (BeOffPropVO) request.getAttribute("vo");
	%>
	道具名称:<%=vo.getPropName()%><br />
	道具描述:<%=vo.getPropDisplay() %><br />
	所需<%=GameConfig.getYuanbaoName() %>:<%=vo.getPropMoney()%><%=GameConfig.getYuanbaoName() %>
	<br />
	获得离线时间:<%=vo.getPropTime()%>小时
	<br />
	请输入你要购买的数量。
	<br />
	<input name="number" type="text" size="4" maxlength="3"/>
	<anchor>
	<go href="<%=response.encodeURL(GameConfig.getContextPath() + "/rolebeoffaction.do?cmd=n3")%>" method="post">
	<postfield name="be_id" value="<%=vo.getBeId()%>" />
	<postfield name="number" value="$number" />
	</go>
	购买
	</anchor><br />
	<anchor>
	<go href="<%=response.encodeURL(GameConfig.getContextPath() + "/login.do?cmd=n6")%>" method="get"></go>
	进入游戏
	</anchor>
	<br />
	<anchor>
	<go href="<%=response.encodeURL(GameConfig.getContextPath() + "/login.do?cmd=n3")%>" method="get"></go>
	返回上一级
	</anchor>
</p>
</card>
</wml>
