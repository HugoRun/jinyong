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
	您确定删除全部邮件？
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mail.do?cmd=n4")%>">
	<postfield name="deleteSure" value="deleteSure" />
	</go>
	确定
	</anchor>
	<br />
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mail.do?cmd=n1")%>" ></go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
