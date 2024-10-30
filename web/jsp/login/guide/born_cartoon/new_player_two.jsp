<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
当您领取一个任务后,您可以点击游戏主界面上方的“任务”,查看您当前拥有的任务,当您点击了一条任务时,您就可以看到该任务的内容,点击任务内容中蓝色的关键字直接传送到任务地点。在任务地点点击带有红色“?”的人物或物品后,点击“下一步”后继续进行任务.<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n6") %>" method="get"></go>
试着做一次任务
</anchor>
</p>
</card>
</wml>