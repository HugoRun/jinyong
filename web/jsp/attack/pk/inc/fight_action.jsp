<%@ page pageEncoding="UTF-8" isErrorPage="false"%>
<%@page import="com.ls.pub.config.GameConfig,com.ls.model.user.RoleEntity" %>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<%
	RoleEntity me = (RoleEntity)request.getAttribute("me");
	RoleEntity other = (RoleEntity)pageContext.getAttribute("other");
	if(me.isPKToOther(other)) 
	{%>
<anchor>攻击
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/pk.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="bPpk" value="${other.PPk}" />
</go>
</anchor>
<%	} %>
