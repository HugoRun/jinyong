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
		List<UserMountsVO> list = (List<UserMountsVO>) request
				.getAttribute("list");
	%>
	【坐骑栏】
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n15" />
	</go>
	换乘
	</anchor>
	|
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n16" />
	<postfield name="args" value="up" />
	</go>
	升级
	</anchor>
	|
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n14" />
	</go>
	购买
	</anchor>
	<br />
	现有坐骑：
	<br />
	<%
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				UserMountsVO uv = list.get(i);
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mounts.do")%>">
	<postfield name="cmd" value="n17" />
	<postfield name="mountsID" value="<%=uv.getId() %>" />
	</go>
	<%=uv.getMountsName()%>
	</anchor>
	<%=uv.getMountsLevle()%>级
	<%
		if (uv.getMountsState() == 0) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig
										.getContextPath()
										+ "/mounts.do")%>">
	<postfield name="cmd" value="n11" />
	<postfield name="mountID" value="<%=uv.getId() %>" />
	</go>
	骑乘
	</anchor>
	|
	<%
		} else {
				%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig
										.getContextPath()
										+ "/mounts.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="mountID" value="<%=uv.getId() %>" />
	</go>
	取消骑乘
	</anchor>
	|
	<%
				}
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mounts.do")%>">
	<postfield name="cmd" value="n12" />
	<postfield name="mountID" value="<%=uv.getId() %>" />
	</go>
	遗弃
	</anchor>
	<br />

	<%
		}
		}
	%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
