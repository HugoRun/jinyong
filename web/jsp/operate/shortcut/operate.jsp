<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="ID" title="<bean:message key="gamename"/>">
<p>
请选择该快捷键设置的种类:<br/>
技能  /点击进入技能列表<br/>
物品  /点击进入物品列表<br/>
查看<br/>
攻击<br/>
返回  /返回快捷键设置<br/>
----------------------<br/>
<%@ include file="/init/init_time.jsp"%> 
</p>
</card>
</wml>
