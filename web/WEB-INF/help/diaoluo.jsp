<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.constant.GoodsType"%>
<%@page import="com.ben.help.Help"%>
请选择您要查询的物品类型：<br/>
<%Help help = (Help)request.getAttribute("help"); %>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n6" />
<postfield name="type" value="<%=GoodsType.EQUIP %>" />
<postfield name="id" value="<%=help.getId() %>" />
</go>武器</anchor>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n6" />
<postfield name="type" value="<%=GoodsType.EQUIP %>" />
<postfield name="id" value="<%=help.getId() %>" />
</go>防具</anchor>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n6" />
<postfield name="type" value="<%=GoodsType.EQUIP %>" />
<postfield name="id" value="<%=help.getId() %>" />
</go>首饰</anchor>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n6" />
<postfield name="type" value="<%=GoodsType.PROP %>" />
<postfield name="id" value="<%=help.getId() %>" />
</go>道具</anchor>
<br/>
<anchor><go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/help.do?cmd=n1")%>" method="get" ></go>返回</anchor><br/>
<anchor><go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get" ></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
