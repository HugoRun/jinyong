<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.util.MoneyUtil,com.ls.ben.vo.mall.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<%
	request.getSession().removeAttribute("return_type");
	CommodityVO CommodityVO = (CommodityVO) request
			.getAttribute("commodity");
	String hint = (String) request.getAttribute("hint");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%=hint%>
	<br />
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/attackNPC.do?cmd=n17")%>"
		method="get"></go>
	返回
	</anchor>
	<br />
	<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>