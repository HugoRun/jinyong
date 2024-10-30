<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.constant.Shortcut"%>
<%@page import="java.util.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<%
		String sc_pk = (String) request.getAttribute("sc_pk");
		String isCatchPet = (String) request.getAttribute("isCatchPet");
	%>
	请选择该快捷键设置的种类:
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/shortcut.do?cmd=n5")%>">
	<postfield name="sc_pk" value="<%=sc_pk%>" />
	</go>
	技能
	</anchor>
	<br />
	药品
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/shortcut.do?cmd=n2")%>">
	<postfield name="sc_pk" value="<%=sc_pk%>" />
	<postfield name="type" value="<%=Shortcut.LOOKINFO%>" />
	</go>
	查看
	</anchor>
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/shortcut.do?cmd=n2")%>">
	<postfield name="sc_pk" value="<%=sc_pk%>" />
	<postfield name="type" value="<%=Shortcut.ATTACK%>" />
	</go>
	攻击
	</anchor>
	<br />
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/attackNPC.do?cmd=n4")%>"></go>
	返回
	</anchor>
	<br />
	<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
