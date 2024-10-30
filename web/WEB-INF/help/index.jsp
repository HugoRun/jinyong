<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="java.util.List"%>
<%@page import="com.ben.help.Help"%>
<%List<Help> list = (List)request.getAttribute("list");
int nowPage = (Integer)request.getAttribute("nowPage");
int allPage = (Integer)request.getAttribute("allPage");
 if(list==null||list.size()==0){%>
 没有菜单 <br/>
 <%}else{ 
 for(Help help : list){
 %>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n2" />
<postfield name="id" value="<%=help.getId() %>" />
</go>
 <%=help.getName() %></anchor><br/>
 

 <%}if(allPage>1){
	if(allPage >nowPage){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do") %>"> 
	<postfield name="nowPage" value="<%=nowPage+1%>" />
	<postfield name="cmd" value="n1" />
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
	<postfield name="cmd" value="n1" />
	</go>
	上一页
	</anchor> 
	<%
	}%>第<%=nowPage %>页/共<%=allPage %>页<%
	} }%>
	<br/>
<anchor><go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get" ></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
