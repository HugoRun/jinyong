<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="java.util.List"%>
<%@page import="com.ben.help.Help"%>
<%List<Help> list = (List)request.getAttribute("list");
int nowPage = (Integer)request.getAttribute("nowPage");
int allPage = (Integer)request.getAttribute("allPage");
Object nowPa = request.getAttribute("nowPa");
Object id = request.getAttribute("id");
Object id1 = request.getAttribute("id1");
 if(list==null||list.size()==0){%>
 没有子菜单 <br/>
 <%}else{ 
 for(Help help : list){
 %>
  <%=help.getName() %>
  <%if(help.getLink_name()!=null&&!"".equals(help.getLink_name().trim())){ %>
<anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do") %>">
    <postfield name="cmd" value="n4" />
    <postfield name="id" value="<%=help.getId() %>" />
    <postfield name="nowPa" value="<%=nowPa%>" />
    <postfield name="id1" value="<%=id1 %>" />
    </go>
    <%=help.getLink_name() %>
    </anchor> <br/>
	<%}else{ %>
 <br/>
<%} %>
 <%}if(allPage>1){
	if(allPage >nowPage){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do") %>"> 
	<postfield name="nowPage" value="<%=nowPage+1%>" />
	<postfield name="cmd" value="n5" />
	<postfield name="nowPa" value="<%=nowPa%>" />
	<postfield name="id" value="<%=id %>" />
	<postfield name="id1" value="<%=id1 %>" />
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
	<postfield name="nowPa" value="<%=nowPa%>" />
	<postfield name="cmd" value="n5" />
	<postfield name="id" value="<%=id %>" />
	<postfield name="id1" value="<%=id1 %>" />
	</go>
	上一页
	</anchor> 
	<%
	}%>第<%=nowPage %>页/共<%=allPage %>页<%
	} }%>
	<br/>
		 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n2" />
<postfield name="id" value="<%=id1 %>" />
<postfield name="nowPage" value="<%=nowPa%>" />
</go>返回</anchor><br/>
<anchor><go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get" ></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
