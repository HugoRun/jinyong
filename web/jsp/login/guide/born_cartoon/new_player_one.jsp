<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
您好,欢迎您进入《<%=GameConfig.getGameName() %>》的新手指导,在游戏中,您可以通过杀怪物、做任务进行升级,等级越高,穿戴的装备也越强大.游戏中带有红色“!”号的人物为可以领取任务的人物,您可以和该人物对话后领取任务.当您完成任务后,可以在带有红色“?”的人物处交还任务,获取任务奖励.<br/>
<anchor> 
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n5") %>">
<postfield name="step" value="6" />
</go>
告诉我应该如何完成任务
</anchor>
</p>
</card>
</wml>
