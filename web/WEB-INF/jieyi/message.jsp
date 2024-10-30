<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ls.ben.vo.system.UMessageInfoVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%
	UMessageInfoVO hint = (UMessageInfoVO)request.getAttribute("result");
	String name = (String)request.getAttribute("name");
	if( hint!=null&&name!=null )
	{
	%>玩家<%=name%>请求与您结义金兰！
<br />
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/jieyi.do")%>">
<postfield name="cmd" value="n9" />
<postfield name="caozuo" value="0" />
<postfield name="operate" value="<%=hint.getMsgOperate1() %>" />
<postfield name="send_id" value="<%=hint.getMsgOperate2() %>" />
<postfield name="name" value="<%=name %>" />
</go>
同意
</anchor>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/jieyi.do")%>">
<postfield name="cmd" value="n9" />
<postfield name="caozuo" value="1" />
<postfield name="operate" value="<%=hint.getMsgOperate1() %>" />
<postfield name="send_id" value="<%=hint.getMsgOperate2() %>" />
</go>
拒绝
</anchor>
<br />
<%
	}
 %>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
