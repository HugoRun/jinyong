<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%
	String pre = (String)request.getAttribute("pre");
	if( pre.equals("rateStone") )
	{
%>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=stoneList")%>" method="get"/></anchor>
<%
	}
	else if( pre.equals("proStone") )
	{
%>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=useProtStoneHint")%>" method="get"/></anchor>
<%
	}
	else if( pre.equals("inlayStone") )
	{
%>
<anchor>返回
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=inlayStoneList")%>" method="post">
<postfield name="pwPk" value="${pwPk}" />
</go>
</anchor>
<%
	}
	else if( pre.equals("upgradeStone") )
	{
%>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/prop.do?cmd=stoneList")%>" method="get"/></anchor>
<%
	}
	else if( pre.equals("fMList") || pre.equals("wMList") )
	{
%>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fStorage.do?cmd="+pre)%>" method="get"/></anchor>
<%
	}
%>
