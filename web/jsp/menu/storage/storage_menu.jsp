<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.pub.constant.Wrap" %> 
<%
	if(request.getAttribute("resultWml")!=null)
	{
		%>
		<%=request.getAttribute("resultWml") %><br/>
		<%
	}
 %>
【仓库】
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do?cmd=n6") %>"></go>增加仓库栏位</anchor><br/>
仓库格数:${warevo.uwWareHouseSpare }/${warevo.uwNumber }<br/> 
<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do?cmd=n1&amp;w_type="+Wrap.CURE)%>"></go>药品</anchor>|
<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do?cmd=n1&amp;w_type="+Wrap.EQUIP)%>"></go>装备</anchor>|
<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do?cmd=n1&amp;w_type="+Wrap.BOOK)%>"></go>仙书</anchor>|
<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do?cmd=n1&amp;w_type="+Wrap.REST)%>"></go>材料</anchor>|
<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do?cmd=n1&amp;w_type="+Wrap.SHOP)%>"></go>商城</anchor><br/>