<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%@page import="com.ben.pk.active.PKVs"%>
<%@page import="com.ls.ben.vo.pkhite.PKHiteVO"%>
<%@page import="com.ls.ben.cache.dynamic.manual.user.RoleCache"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.pub.util.StringUtil"%>
<%@page import="com.ls.ben.vo.mounts.UserMountsVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="act" title="<bean:message key="gamename"/>">
<p>
	<%
		String message=(String)request.getAttribute("message");
		String isUpLeve=(String)request.getAttribute("isUpLeve");
	 %>
	 <%=message%>
	 <br/>
	<%
	if(isUpLeve!=null)
	{
		%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do")%>">
	<postfield name="cmd" value="n16" />
	<postfield name="args" value="up" />
	</go>
	返回
	</anchor>
		<%
	}
	else
	{
		%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do")%>">
	<postfield name="cmd" value="n16" />
	</go>
	返回
	</anchor>
		<%
	}
	 %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
