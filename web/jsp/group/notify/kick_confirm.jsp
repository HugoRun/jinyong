<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="ID" title="<s:message key = "gamename"/>">
<p>
<%
		String member_pk = (String) request.getAttribute("member_pk");
		String member_name = (String) request.getAttribute("member_name");

		if (member_pk != null) {
%>
您确定将玩家<%= member_name%>移出退伍？<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n4")%>">
<postfield name="member_pk" value="<%=member_pk %>" />
</go>
确定
</anchor>
	<%
		out.print("空指针");
		}
	%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n4")%>">
<postfield name="member_pk" value="" />
</go>
返回队友列表
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
