<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>

<%
	response.setContentType("text/vnd.wap.wml");
	String hint = (String) request.getAttribute("hint");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%=hint%><br />
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/communioninfoaction.do?cmd=n1")%>" ></go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
