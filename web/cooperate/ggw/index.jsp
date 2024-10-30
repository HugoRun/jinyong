<?xml version="1.0" ?>
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>
<%
	RoleService roleService = new RoleService();
	RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
	String user_name = (String)session.getAttribute("ssid");
	response.sendRedirect("http://121.9.227.39/gamepay.php?username="+user_name+"&gamename="+roleInfo.getBasicInfo().getName());//跳转到个个玩充值
%>