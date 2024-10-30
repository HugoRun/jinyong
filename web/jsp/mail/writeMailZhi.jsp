<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	玩家:
	<input name="receive_name" type="text" maxlength="18" />
	<br />
	内容:
	<input name="mail_content" type="text" maxlength="50" size="50" />
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/sendMail.do?cmd=n2")%>">
	<postfield name="receive_name" value="$receive_name" />
	<postfield name="mail_content" value="$mail_content" />
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
