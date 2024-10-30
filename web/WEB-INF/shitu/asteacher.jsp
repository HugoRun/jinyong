<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.shitu.model.ShituConstant"%>
<%@page import="com.ben.shitu.model.Shitu"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
List<Shitu> list = (List<Shitu>)request.getAttribute("list");
String message = (String)request.getAttribute("message");
if(message!=null&&!"".equals(message.trim())){
%>
<%=message %><br/>
 <%} %>
徒弟：<br/>
<%if(list!=null&&list.size()>0){
for(Shitu st : list){
%>
<anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>">
	<postfield name="cmd" value="n6" />
	<postfield name="stu_id" value="<%=st.getStu_id() %>" />
	<postfield name="id" value="<%=st.getId() %>" />
	</go>
	<%=st.getStu_name()%>
	</anchor> 
	<br/>
<%}
}else{ %>
没有徒弟
<%} %><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/function/function.jsp")%>" method="get"></go>返回</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
