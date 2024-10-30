<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.util.DateUtil"%>
<%@page import="com.ls.pub.constant.Channel"%>
----------------------<br/>
<%
	if(GameConfig.getChannelId()==Channel.JUU)
	{
		%>
		聚游报时:<%=DateUtil.getMainPageCurTimeStr()%>
		<%
	}
	else
	{
		%>
		报时:<%=DateUtil.getMainPageCurTimeStr()%>
		<%
	}
 %>
