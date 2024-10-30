<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="ID" title="<bean:message key="gamename"/>">
<p>
多谢您向GM反馈问题,我们将尽最大的努力为大家创造一个神奇的洪荒世界!您的每封正当邮件我们都将派专人回复!<br/>
请输入您反馈的问题和建议(500字以内):<br/>
内容:<input name="content" type="text" /><br/>
<anchor>提交
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/gmmail.do?cmd=n2")%>">
<postfield name="content" value="$(content)" />
</go>
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>