<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<% RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
		String lotterydisplay = (String) request.getAttribute("lotterydisplay");
	%><%=roleInfo.getBasicInfo().getName()%>,欢迎您参加竞猜活动!<br/>
<%=lotterydisplay%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
