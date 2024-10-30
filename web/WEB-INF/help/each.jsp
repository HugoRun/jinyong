<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="java.util.List"%>
<%@page import="com.ben.help.Help"%>
<%@page import="com.ben.help.HelpConstant"%>
<%List<Help> list = (List)request.getAttribute("list");
int nowPage = (Integer)request.getAttribute("nowPage");
int allPage = (Integer)request.getAttribute("allPage");
Object id = request.getAttribute("id");
 if(list==null||list.size()==0){%>
 没有子菜单 <br/>
 <%}else{ 
 for(Help help : list){
 %>
 <%if(help.getType()==HelpConstant.TASK){ %>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n5" />
<postfield name="id" value="<%=help.getId() %>" />
<postfield name="nowPa" value="<%=nowPage%>" />
<postfield name="id1" value="<%=id%>" />
 </go><%=help.getName() %></anchor><br/>
 <%}else{ %>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n3" />
<postfield name="id" value="<%=help.getId() %>" />
<postfield name="nowPage" value="<%=nowPage%>" />
</go>
 <%=help.getName() %></anchor><br/>
 <%} %>

 <%}if(allPage>1){
	if(allPage >nowPage){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do") %>"> 
	<postfield name="nowPage" value="<%=nowPage+1%>" />
	<postfield name="cmd" value="n2" />
	<postfield name="id" value="<%=id %>" />
	</go>
	下一页
	</anchor> 
	<%
	}
	if(nowPage > 1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do") %>"> 
	<postfield name="nowPage" value="<%=nowPage-1%>" />
	<postfield name="cmd" value="n2" />
	<postfield name="id" value="<%=id %>" />
	</go>
	上一页
	</anchor> 
	<%
	}%>第<%=nowPage %>页/共<%=allPage %>页<%
	} }%>
	<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do?cmd=n1")%>" method="get"></go>返回</anchor><br/>
<anchor><go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get" ></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
