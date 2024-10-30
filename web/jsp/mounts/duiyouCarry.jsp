<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.model.group.GroupModel"%>
<%@page import="java.util.*"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="wrap" title="<bean:message key="gamename"/>">
<p>
请选择您要传送的队友所在地点：<br/>
<%
		String mountID=(String)request.getAttribute("mountID");
		String mountState=(String)request.getAttribute("mountState");
		GroupModel group_info = (GroupModel)request.getAttribute("group_info");
		String self_id = (String)session.getAttribute("pPk");
		
		if( group_info!=null )
		{
			List<RoleEntity> role_list = group_info.getMemberList();
			int p_pk = -1;
			for (RoleEntity role:role_list) 
			{
				p_pk = role.getBasicInfo().getPPk();
				if( !self_id.equals(p_pk+"") )
				{
%>
<%=role.getBasicInfo().getName() %>(<%=role.getBasicInfo().getSceneInfo().getSceneName() %>)
<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do")%>">
	<postfield name="cmd" value="n8" />
	<postfield name="mountsID" value="<%=mountID%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="scenceID" value="<%=role.getBasicInfo().getSceneId()%>" />
	</go>
	前往
	</anchor>
<br/>
<%
				}
			}
		}
		else
		{
%>
队伍已解散<br/>
<%
		}
	%>
		<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n15" />
	</go>
	返回
	</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
