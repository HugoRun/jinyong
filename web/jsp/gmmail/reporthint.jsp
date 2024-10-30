<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="ID" title="<s:message key = "gamename"/>">
<p>
	<%
		String hint = (String) request.getAttribute("hint");
		if (hint == null) {
			hint = "发送失败!";
	%>
	<%=hint%>
	<%
		} else {
	%>
	<%=hint+"感谢您的参与！祝您游戏愉快!"%>
	<%
		}
	%>
	<br />
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/jsp/gmmail/bugreport.jsp")%>" ></go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
