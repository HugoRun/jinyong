<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.shitu.model.ShituConstant"%>
<%@page import="com.ben.vo.friend.FriendVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
String te_name = (String)request.getAttribute("te_name");
Object te_id = request.getAttribute("te_id");
Object id = request.getAttribute("id");
%>
徒弟主动解除师徒关系将增加<%=ShituConstant.STU_ZUIE %>的罪恶值，你确定要解除与<%=te_name %>的师徒关系吗？<br/>
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>"> 
	<postfield name="cmd" value="n10" />
	<postfield name="te_id" value="<%=te_id %>" />
	<postfield name="id" value="<%=id%>" />
	</go>
	确定
	</anchor> 
	<br/>
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>"> 
	<postfield name="cmd" value="n5" />
	</go>
	返回
	</anchor> 
	<br/>

<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
