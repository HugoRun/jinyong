<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="java.util.List"%>
<%@page import="com.ben.help.Help"%>
<%@page import="com.ben.help.HelpUtil"%>
<%@page import="com.ben.help.HelpConstant"%>
<%Help help = (Help)request.getAttribute("help");
String nowp = (String)request.getAttribute("nowp");
Object nowPage = request.getAttribute("nowPage");
%>
<%=HelpUtil.getDes(help.getDes(),nowp,help.getId(),nowPage) %><br/>
<%if(help.getLink_name()!=null&&!"".equals(help.getLink_name().trim())){
if(help.getLink_name().trim().equals(HelpConstant.TESHU.trim())){
 %>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n0")%>" method="get"></go><%=help.getLink_name() %></anchor><br/>
 <%}else{ %>
<anchor> 
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do") %>">
    <postfield name="cmd" value="n4" />
    <postfield name="id" value="<%=help.getId() %>" />
    <postfield name="id1" value="<%=help.getSuper_id() %>" />
    <postfield name="nowPa" value="<%=nowp%>" />
    </go>
    <%=help.getLink_name() %>
    </anchor> <br/>
	<%}}else{ %>
	<%} %>
	 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n2" />
<postfield name="id" value="<%=help.getSuper_id() %>" />
<postfield name="nowPage" value="<%=nowPage%>" />
</go>返回</anchor><br/>
<anchor><go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get" ></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
