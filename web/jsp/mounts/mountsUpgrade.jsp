<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.model.group.GroupModel"%>
<%@page import="java.util.*"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.ben.vo.mounts.UserMountsVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="wrap" title="<s:message key = "gamename"/>">
<p>

	<%
		List<UserMountsVO> list = (List<UserMountsVO>) request
				.getAttribute("list");
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				UserMountsVO uv = list.get(i);
	%>
	【<%=uv.getMountsName()%>】<%=uv.getMountsLevle()%>级
	<%
		if (uv.getMountsLevle() < 5) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig
										.getContextPath()
										+ "/mounts.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="mountsID" value="<%=uv.getId() %>" />
	<postfield name="nextLevelMountsID" value="<%=uv.getNextLevelID()%>" />
	</go>
	升级
	</anchor>

	<%
		}
	%>

	<%
		%><br/><%
		}
		}
	%>	
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n16" />
	</go><br/>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
