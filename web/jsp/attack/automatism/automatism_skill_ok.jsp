<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
	<%
		String pPk = (String) request.getAttribute("pPk");
		String scType = (String) request.getAttribute("scType");
		String operateId = (String) request.getAttribute("operateId");
		String killName = (String) request.getAttribute("killName");
	%>
	您选择在自动战斗中使用<%=killName%>！
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/attackNPC.do")%>">
	<postfield name="cmd" value="n12" />
	<postfield name="pPk" value="<%=pPk%>" />
	<postfield name="scType" value="<%=scType%>" />
	<postfield name="operateId" value="<%=operateId%>" />
	</go>
	开始自动战斗
	</anchor>
	<br />
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/attackNPC.do?cmd=n4")%>" ></go>
	返回
	</anchor>
	<br />
	<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
