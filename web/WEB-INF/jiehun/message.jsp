<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ls.ben.vo.system.UMessageInfoVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
    UMessageInfoVO hint = (UMessageInfoVO)request.getAttribute("result");
    String name = (String)request.getAttribute("name");
    String ownname = (String)request.getAttribute("ownname");
    if( hint!=null&&name!=null )
	{
	%>
	红娘：恭喜你，贺喜你～<%=ownname%>小姐，我今天可是有个天大的好消息给你哦！<%=name%>看中了你，真是恭喜你啊～<%=name%>那可是一表人才，要钱有钱，要德有德，不知道姑娘意下如何？
	<br/>
	<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do")%>">
<postfield name="cmd" value="nn3" />
	 <postfield name="caozuo" value="0" />
	 <postfield name="send_id" value="<%=hint.getMsgOperate2() %>" />
	 </go>
我也中意他</anchor><br/>
<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do")%>">
<postfield name="cmd" value="nn3" />
	 <postfield name="caozuo" value="1" />
	 <postfield name="send_id" value="<%=hint.getMsgOperate2() %>" />
	 </go>
我瞧不上他</anchor><br/>
	<%
	}
 %>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
