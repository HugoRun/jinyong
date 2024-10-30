<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.shitu.model.Shitu"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%

List<Shitu> list = (List<Shitu>)request.getAttribute("list");
int nowPage = (Integer)request.getAttribute("nowPage");
int allPage = (Integer)request.getAttribute("allPage");
String message = (String)request.getAttribute("message");
if(message!=null&&!"".equals(message.trim())){
%>
<%=message %><br/>
 <%} %>
请选择你要招收的徒弟：<br/>
<%if(list!=null&&list.size()>0){ 
for(Shitu st : list){
%>
<anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>">
    <postfield name="cmd" value="n3" />
    <postfield name="stu_id" value="<%=st.getStu_id() %>" />
    <postfield name="id" value="<%=st.getId() %>" />
    </go>
    <%=st.getStu_name()%>(<%=st.getStu_level()%>)
    </anchor>
    <br/>
<%}
    if(allPage>1){
    if(allPage >nowPage){
    %>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>">
    <postfield name="nowPage" value="<%=nowPage+1%>" />
    <postfield name="cmd" value="n2" />
    </go>
	下一页
	</anchor> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>"> 
	<postfield name="nowPage" value="<%=allPage%>" />
	<postfield name="cmd" value="n2" />
	</go>
	末页
	</anchor>
	<%
	}
	if(nowPage > 1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>"> 
	<postfield name="nowPage" value="<%=nowPage-1%>" />
	<postfield name="cmd" value="n2" />
	</go>
	上一页
	</anchor> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>"> 
	<postfield name="nowPage" value="1" />
	<postfield name="cmd" value="n2" />
	</go>
	首页
	</anchor> 
	<%
	}%>第<%=nowPage %>页/共<%=allPage %>页<%
	}
}else{ %>
没有拜师的玩家
<%} %><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
