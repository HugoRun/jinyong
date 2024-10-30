<%@page contentType="text/vnd.wap.wml"
	import="com.ls.pub.constant.Channel" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%
	String return_type = (String) session.getAttribute("return_type");
	RoleService roleServices = new RoleService();
	RoleEntity roleInfos = roleServices.getRoleInfoBySession(request.getSession());
%>
<%
	if (return_type != null && return_type.equals("1")) {
%>
<anchor>
<go method="get"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/attackMallAction.do?cmd=n3")%>" ></go>
返回游戏
</anchor>
<br />
<%
	} 
	else if(GameConfig.getChannelId()==Channel.TELE&&roleInfos==null)
	{
		%>
		<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/cooperate/tele/index/pointPage.jsp")%>" method="get"></go>返回上一级</anchor><br/>
		<%
	}
	else {
%>
<anchor>
<go method="get"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mall.do?cmd=n0")%>" ></go>
返回商城
</anchor>
<br />
<%
	}
%>