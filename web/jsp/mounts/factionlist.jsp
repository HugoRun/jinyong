<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" isELIgnored ="false" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.model.group.GroupModel"%>
<%@page import="java.util.*"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<card id="wrap" title="<s:message key = "gamename"/>">
<p>
请选择您要传送的氏族成员所在地点：<br/>
<c:forEach items="${item_page.result}" var="item">
${ item.display}
<c:choose>
<c:when test="${item.loginState==1}">
<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do")%>">
	<postfield name="cmd" value="n8" />
	<postfield name="mountsID" value="${mountID}" />
	<postfield name="mountState" value="${mountState}" />
	<postfield name="scenceID" value="${item.PMap}" />
	</go>
	前往
	</anchor><br/>
</c:when>
<c:otherwise>
离线<br/>
</c:otherwise>
</c:choose>
</c:forEach>
${item_page.pageFoot }
		<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n15" />
	</go>
	返回
	</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
