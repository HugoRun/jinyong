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
<%@page import="com.ls.ben.vo.mounts.MountsVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="act" title="<bean:message key="gamename"/>">
<p>
	<%
		List<MountsVO> list = (List<MountsVO>) request.getAttribute("list");
		for (int i = 0; i < list.size(); i++) {
			MountsVO mv = list.get(i);
			if (mv.getType() == 1) {
	%>
	走兽：【<%=mv.getName()%>】(<%=mv.getLevel()%>级)<%=mv.getSentPrice()%>仙晶
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mounts.do")%>">
	<postfield name="cmd" value="n13" />
	<postfield name="mountID" value="<%=mv.getId()%>" />
	</go>
	购买
	</anchor><br/>

	<%
		}
			if (mv.getType() == 2) {
	%>
	飞禽：【<%=mv.getName()%>】(<%=mv.getLevel()%>级)<%=mv.getSentPrice()%>仙晶
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mounts.do")%>">
	<postfield name="cmd" value="n13" />
	<postfield name="mountID" value="<%=mv.getId()%>" />
	</go>
	购买
	</anchor><br/>

	<%
		}
			if (mv.getType() == 3) {
	%>
	鳞甲：【<%=mv.getName()%>】(<%=mv.getLevel()%>级)<%=mv.getSentPrice()%>仙晶
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mounts.do")%>">
	<postfield name="cmd" value="n13" />
	<postfield name="mountID" value="<%=mv.getId()%>" />
	</go>
	购买
	</anchor>

	<%
		}
		}
	%>
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n16" />
	</go>
	返回
	</anchor>
	<br />
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
