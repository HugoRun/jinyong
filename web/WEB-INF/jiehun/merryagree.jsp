<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ls.ben.vo.system.UMessageInfoVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	UMessageInfoVO hint = (UMessageInfoVO)request.getAttribute("result");
	String name = (String)request.getAttribute("name");
	String ownname = (String)request.getAttribute("ownname");
	if( hint!=null&&hint.getMsgOperate1().trim().equals("0") )
	{
	%>
	红娘：恭喜你了，<%=ownname%>，<%=name %>小姐已经同意了。我看今天的日子就不错，你们就乘机把喜事给办了吧。凤冠×1、状元袍×1、龙凤烛×2、结婚戒指×2、银两888你带了吗？
	<br/>
	<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do")%>">
<postfield name="cmd" value="nn4" />
	 <postfield name="caozuo" value="0" />
	 <postfield name="send_id" value="<%=hint.getMsgOperate2() %>" />
	 </go>
带了</anchor><br/>
<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do")%>">
<postfield name="cmd" value="nn4" />
	 <postfield name="caozuo" value="1" />
	 <postfield name="send_id" value="<%=hint.getMsgOperate2() %>" />
	 </go>
没带</anchor><br/>
	<%
	}else{
 %>
 红娘：哎呦，<%=ownname %>，<%=name %>小姐瞧不上你，我好话说尽也不管用！你还是再去找个对你有意思的再来我这吧！<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
 
 <%} %>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
