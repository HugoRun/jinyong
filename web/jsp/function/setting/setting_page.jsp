<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="setting" title="<bean:message key="gamename"/>">
<p>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=n1")%>" method="get"></go>
图片设置
</anchor>
<br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do?cmd=n1&amp;shortType=1")%>" method="get"></go>
快捷键设置
</anchor>
<br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=n4")%>" method="get"></go> 
聊天设置
</anchor>
<br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=n6")%>" method="get"></go>
其他设置
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=index")%>" method="get"></go>返回上级</anchor>  
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
