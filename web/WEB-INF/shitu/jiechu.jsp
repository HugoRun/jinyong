<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.shitu.model.ShituConstant"%>
<%@page import="com.ben.vo.friend.FriendVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
String stu_name = (String)request.getAttribute("stu_name");
Object stu_id = request.getAttribute("stu_id");
Object id = request.getAttribute("id");
%>
师傅主动解除与徒弟的师徒关系将增加<%=ShituConstant.TEA_ZUIE %>的罪恶值，你确定要解除与<%=stu_name %>的师徒关系吗？<br/>
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>"> 
	<postfield name="cmd" value="n8" />
	<postfield name="stu_id" value="<%=stu_id %>" />
	<postfield name="id" value="<%=id%>" />
	</go>
	确定
	</anchor> 
	<br/>
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>"> 
	<postfield name="cmd" value="n6" />
	<postfield name="stu_id" value="<%=stu_id %>" />
	<postfield name="id" value="<%=id%>" />
	</go>
	返回
	</anchor> 
	<br/>

<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
