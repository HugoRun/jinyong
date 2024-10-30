<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.ben.help.Guai"%>
<%@page import="com.ben.help.HelpConstant"%>
<%String message = (String)request.getAttribute("message"); 
Object type = request.getAttribute("type");
Object id = request.getAttribute("id");
int prop_class = (request.getAttribute("prop_class")==null?0:(Integer)request.getAttribute("prop_class"));
int prop_id = (request.getAttribute("prop_id")==null?0:(Integer)request.getAttribute("prop_id"));
%>
<%if(message!=null&&!"".equals(message.trim())){ %>
<%=message==null?"":message.trim()+"<br/>" %>
<%}else{ %>
<%String display = (String)request.getAttribute("display"); %>
<%=display==null?"":display.trim() %>
----------------------<br/>
<%
//可以查看所有物品的掉落信息
if(true)//HelpConstant.SHOW.contains(prop_id)||prop_class==0||prop_class==1||prop_class==2||prop_class==8||prop_class==24||prop_class==30)
{
List<Guai> list = (List)request.getAttribute("list"); 
if(list!=null&&list.size()>0){%>
掉落:<br/>
<%if(prop_id==HelpConstant.HUANGJINBAOXIANG){ %>
游戏中的所有BOSS均可掉落<br/>
<%}else if(prop_id==HelpConstant.JINDAN) {%>
游戏中的所有怪物均可掉落<br/>
<%} %>
<%for(Guai guai : list)
{
%>
<%=guai.getDes() %><br/>
<%
}
}else{
%>
暂无该物品掉落信息<br/>
<%
}}else{%>
暂无该物品掉落信息<br/>
<%} %>
<%} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n6" />
<postfield name="type" value="<%=type %>" />
<postfield name="id" value="<%=id %>" />
</go>返回</anchor><br/>
<anchor><go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get" ></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
