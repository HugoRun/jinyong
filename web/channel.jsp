<?xml version="1.0" ?>
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
if(GameConfig.getChannelId() == 16){
	String ownerId = request.getParameter("tx-owner");
	String appId = request.getParameter("tx-app");
	String sessionId = request.getParameter("tx-session");
	String uid = request.getParameter("uid");
	String url = GameConfig.getContextPath()+"/tx/login.do?tx-owner="+ownerId+"&tx-app="+appId+"&tx-session="+sessionId+"&uid="+uid;
	response.sendRedirect(url);//跳转到当乐专区
}else{
if(GameConfig.getChannelId() != 10){
	String url = GameConfig.getUrlOfLoginPlatform();
	response.sendRedirect(url);//跳转到当乐专区
}
else{
	String url = GameConfig.getContextPath()+"/air/login.do";
	response.sendRedirect(url);//跳转到当乐专区
}
}
%>