<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="hint" title="<s:message key = "gamename"/>">
<p>
重复提交!<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/backActive.do") %>" method="get"></go>返回</anchor>
</p>
</card>
</wml>
