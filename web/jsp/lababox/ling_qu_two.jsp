<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="map">
<%
	String mes = (String) request.getAttribute("mes");
%>
<p>
	【虎运宝箱】
	<br />
	<%=mes%>
	<br />
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/laba.do?cmd=n4")%>">
	是(点击，将自动跳到商城)
	</go>
	</anchor>
	<br />
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/laba.do?cmd=n3")%>">
	否(点击，将获得您最后一次刷出的物品)
	</go>
	</anchor>
</p>
</card>
</wml>
