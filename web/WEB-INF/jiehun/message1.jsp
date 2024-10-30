
<%@page import="com.ben.vo.friend.FriendVO"%><%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ls.ben.vo.system.UMessageInfoVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%  FriendVO fo = (FriendVO)request.getAttribute("friendVo");
	UMessageInfoVO hint = (UMessageInfoVO)request.getAttribute("result");
	String name = (String)request.getAttribute("name");
	if( hint!=null&&name!=null )
	{
	%><%=name%>申请与您离婚
	<br/>
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do") %>"> 
	<postfield name="pByPk" value="<%=hint.getMsgOperate2() %>" />
	<postfield name="pByName" value="<%=name%>" />
	<postfield name="cmd" value="n11" />
	</go>
	同意
	</anchor> 
	
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do") %>"> 
	<postfield name="pByPk" value="<%=hint.getMsgOperate2() %>" />
	<postfield name="pByName" value="<%=name%>" />
	<postfield name="cmd" value="n14" />
	</go>
	拒绝
	</anchor> 
	<%
	}
 %>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
