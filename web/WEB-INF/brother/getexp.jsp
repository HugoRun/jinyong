<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.vo.friend.FriendVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
Object w_type =  request.getAttribute("w_type");
Object propUseEffect =  request.getAttribute("propUseEffect");
Object pg_pk =  request.getAttribute("pg_pk");
Object goods_id =  request.getAttribute("goods_id");
Object goods_type =  request.getAttribute("goods_type");
Object page_no = (String)request.getSession().getAttribute("page_no");
String message = (String)request.getAttribute("message");
if(message!=null&&!"".equals(message.trim())){
%>
<%=message %><br/>
 <%} %>
<anchor>
        <go method="post" href="/wrap.do">
        <postfield name="cmd" value="n1" />
        <postfield name="w_type" value="<%=w_type %>" />
        <postfield name="pg_pk" value="<%=pg_pk %>" />
        <postfield name="goods_id" value="<%=goods_id %>" />
        <postfield name="goods_type" value="<%=goods_type %>" />
        <postfield name="page_no" value="<%=page_no %>" />
        </go>
        返回
        </anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
