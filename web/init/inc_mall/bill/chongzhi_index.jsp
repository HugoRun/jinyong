<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.constant.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
    String channel_id = (String)session.getAttribute("channel_id");

    if( channel_id.equals(Channel.SKY+"") )//思凯的充值通道
    {
    %><%@ include file="sky_chongzhi.jsp"%><%
    }
    else
    {
    %><%@ include file="dl_chongzhi.jsp"%><%//当乐的充值通道
    }
%>