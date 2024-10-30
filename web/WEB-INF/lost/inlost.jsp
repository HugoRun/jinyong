<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.menu.OperateMenuVO"%>
<%String w_type = (String)request.getAttribute("w_type");
String pg_pk = (String)request.getAttribute("pg_pk");
String goods_id = (String)request.getAttribute("goods_id");
String goods_type = (String)request.getAttribute("goods_type");

 %>
您如果使用传送道具离开迷宫，您的秘境地图将会被系统回收，您是否确定继续传送
<br />
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="w_type" value="<%=w_type %>" />
<postfield name="pg_pk" value="<%=pg_pk %>" />
<postfield name="goods_id" value="<%=goods_id %>" />
<postfield name="goods_type" value="<%=goods_type %>" />
<postfield name="commit" value="111" />
</go>
是
</anchor>
<br />

<anchor>
<go    href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get"></go>
否
</anchor>
<br />
<anchor>
<go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get"></go>
返回游戏
</anchor>
<br />
<%@ include file="/WEB-INF/inc/footer.jsp"%>
