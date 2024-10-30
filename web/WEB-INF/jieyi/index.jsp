<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%
	String hint = (String) request.getAttribute("message");
	if (hint != null && !"".equals(hint.trim())) {
%>
<%=hint%><br />
<%
	}
%>
请将结义所需物品放在下面正确的位置上：
<br />
<%
	PlayerPropGroupVO ppv = (PlayerPropGroupVO) request
			.getAttribute("good1");
	if (ppv != null) {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="option" value="1" />
<postfield name="propgroup" value="<%=ppv.getPgPk()%>" />
<postfield name="good_id" value="<%=ppv.getPropId()%>" />
<postfield name="caozuo" value="2" />
</go>
<%=ppv.getPropName()%></anchor>

<%
	} else {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="option" value="1" />
</go>
物品栏
</anchor>
<%
	}
%>
<%
	PlayerPropGroupVO ppv2 = (PlayerPropGroupVO) request
			.getAttribute("good2");
	if (ppv2 != null) {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="option" value="2" />
<postfield name="propgroup" value="<%=ppv2.getPgPk()%>" />
<postfield name="good_id" value="<%=ppv2.getPropId()%>" />
<postfield name="caozuo" value="2" />
</go>
<%=ppv2.getPropName()%></anchor>

<%
	} else {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="option" value="2" />
</go>
物品栏
</anchor>
<%
	}
%>
<%
	PlayerPropGroupVO ppv3 = (PlayerPropGroupVO) request
			.getAttribute("good3");
	if (ppv3 != null) {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="option" value="3" />
<postfield name="propgroup" value="<%=ppv3.getPgPk()%>" />
<postfield name="good_id" value="<%=ppv3.getPropId()%>" />
<postfield name="caozuo" value="2" />
</go>
<%=ppv3.getPropName()%></anchor>

<%
	} else {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="option" value="3" />
</go>
物品栏
</anchor>
<%
	}
%>
<br />
<%
	PlayerPropGroupVO ppv4 = (PlayerPropGroupVO) request
			.getAttribute("good4");
	if (ppv4 != null) {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="option" value="4" />
<postfield name="propgroup" value="<%=ppv4.getPgPk()%>" />
<postfield name="good_id" value="<%=ppv4.getPropId()%>" />
<postfield name="caozuo" value="2" />
</go>
<%=ppv4.getPropName()%></anchor>

<%
	} else {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="option" value="4" />
</go>
物品栏
</anchor>
<%
	}
%>
<%
	PlayerPropGroupVO ppv5 = (PlayerPropGroupVO) request
			.getAttribute("good5");
	if (ppv5 != null) {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="option" value="5" />
<postfield name="propgroup" value="<%=ppv5.getPgPk()%>" />
<postfield name="good_id" value="<%=ppv5.getPropId()%>" />
<postfield name="caozuo" value="2" />
</go>
<%=ppv5.getPropName()%></anchor>

<%
	} else {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="option" value="5" />
</go>
物品栏
</anchor>
<%
	}
%>
<%
	PlayerPropGroupVO ppv6 = (PlayerPropGroupVO) request
			.getAttribute("good6");
	if (ppv6 != null) {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="option" value="6" />
<postfield name="propgroup" value="<%=ppv6.getPgPk()%>" />
<postfield name="good_id" value="<%=ppv6.getPropId()%>" />
<postfield name="caozuo" value="2" />
</go>
<%=ppv6.getPropName()%></anchor>

<%
	} else {
%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/jieyi.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="option" value="6" />
</go>
物品栏
</anchor>
<%
	}
%>
<br />

<anchor>
<go
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/jieyi.do?cmd=n6")%>"
	method="get"></go>
确定
</anchor>
<br />
<anchor>
<go
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/pubbuckaction.do")%>"
	method="get"></go>
返回游戏
</anchor>
<br />
<%@ include file="/WEB-INF/inc/footer.jsp"%>
