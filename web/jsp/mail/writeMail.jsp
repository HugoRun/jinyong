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
		String sendPk = (String) request.getAttribute("sendPk");
		String senderName = (String) request.getAttribute("senderName");
		String mail_type = (String) request.getAttribute("mail_type");
		String from_type = (String) request.getAttribute("from_type");

		if (mail_type.equals("1")) {
	%>
	玩家:<%=senderName%><br />
	内容:
	<input name="mail_content" type="text" size="50" maxlength="50" />
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/sendMail.do?cmd=n1")%>">
	<postfield name="sendPk" value="<%=sendPk%>" />
	<postfield name="mail_type" value="<%=mail_type%>" />
	<postfield name="mail_content" value="$mail_content" />
	</go>
	确定
	</anchor>
	<br />
	<%
		if (from_type.equals("2")) {
	%>
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mail.do?cmd=n1")%>" ></go>
	返回
	</anchor>
	<%
		} else {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/friendaction.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="pByPk" value="<%=sendPk%>" />
	<postfield name="pByName" value="<%=senderName%>" />
	</go>
	返回
	</anchor>
	<%
		}
		} else {
	%>
	系统邮件，无需回复！
	<br />
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mail.do?cmd=n1")%>" ></go>
	返回
	</anchor>
	<%
		}
	%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
