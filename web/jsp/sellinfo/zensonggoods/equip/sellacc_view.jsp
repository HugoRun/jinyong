<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"  pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
<%
	String equip_display = (String)request.getAttribute("equip_display");
	String sPk = (String)request.getAttribute("sPk");
%> 
<%=equip_display %><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
<postfield name="cmd" value="n11" />
<postfield name="sPk" value="<%=sPk %>" />
</go>
返回
</anchor>
</p>
</card>
</wml>
