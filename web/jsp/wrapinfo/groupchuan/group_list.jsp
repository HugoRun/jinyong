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
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="wrap" title="<s:message key = "gamename"/>">
<p>
<%
		GroupModel group_info = (GroupModel)request.getAttribute("group_info");
		String pg_pk = (String)request.getAttribute("pg_pk");
		
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
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/groupchuan.do?cmd=n2")%>" >
<postfield name="friend_pk" value="<%=role.getBasicInfo().getPPk() %>" />
<postfield name="pg_pk" value="<%=pg_pk %>" />
</go>
<%=role.getBasicInfo().getName() %>
(<%=role.getBasicInfo().getSceneInfo().getSceneName() %>)
</anchor><br/>
<%
				}
			}
		}
		else
		{
%>
队伍已解散
<%
		}
	%>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1") %>"></go></anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
