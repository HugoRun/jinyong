<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>  
游戏商城道具:<br/>
<%
	String mallType=(String)request.getAttribute("type");
	if("1".equals(mallType))
	{
		%>
装备
|
<anchor>祝福
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="2"/>
</go></anchor>
|
<anchor>氏族
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="4"/>
</go></anchor>
|
<anchor>活动
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="3"/>
</go></anchor>
|
<anchor>会员
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="6"/>
</go></anchor>
|
<anchor>积分
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="5"/>
</go></anchor>

<br/>
		<%
	}
	else if("2".equals(mallType))
	{
		%>
<anchor>装备
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="1"/>
</go></anchor>
|
祝福
|
<anchor>氏族
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="4"/>
</go></anchor>
|
<anchor>活动
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="3"/>
</go></anchor>
|
<anchor>会员
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="6"/>
</go></anchor>
|
<anchor>积分
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="5"/>
</go></anchor>
<br/>
		<%
	}
	else if("3".equals(mallType))
	{
		%>
<anchor>装备
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="1"/>
</go></anchor>
|
<anchor>祝福
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="2"/>
</go></anchor>
|
<anchor>氏族
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="4"/>
</go></anchor>
|
活动
|
<anchor>会员
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="6"/>
</go></anchor>
|
<anchor>积分
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="5"/>
</go></anchor>
<br/>
		<%
	}
	else if("4".equals(mallType))
	{
		%>
<anchor>装备
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="1"/>
</go></anchor>
|
<anchor>祝福
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="2"/>
</go></anchor>
|
氏族
|
<anchor>活动
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="3"/>
</go></anchor>
|
<anchor>会员
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="6"/>
</go></anchor>
|
<anchor>积分
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="5"/>
</go></anchor>
<br/>
		<%
	}
	else if("5".equals(mallType))
	{
		%>
<anchor>装备
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="1"/>
</go></anchor>
|
<anchor>祝福
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="2"/>
</go></anchor>
|
<anchor>氏族
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="4"/>
</go></anchor>
|
<anchor>活动
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="3"/>
</go></anchor>
|
<anchor>会员
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="6"/>
</go></anchor>
|
积分
<br/>
		<%
	}
	else
	{
		%>
<anchor>装备
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="1"/>
</go></anchor>
|
<anchor>祝福
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="2"/>
</go></anchor>
|
<anchor>氏族
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="4"/>
</go></anchor>
|
<anchor>活动
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="3"/>
</go></anchor>
|
会员
|
<anchor>积分
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="5"/>
</go></anchor>
<br/>
		<%
	}
 %>

