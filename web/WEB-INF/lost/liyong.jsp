<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.menu.OperateMenuVO"%>
<%
OperateMenuVO menu = (OperateMenuVO)request.getAttribute("menu");
%>
<%=menu.getMenuName() %>：<%=menu.getMenuDialog() %><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/lost.do")%>" method="post">
<postfield name="cmd" value="n3" />
<postfield name="menu_id" value="<%=menu.getId() %>" />
</go>
回答问题
</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
