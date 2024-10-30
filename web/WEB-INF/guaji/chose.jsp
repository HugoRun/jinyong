<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.map.SceneVO"%>
<%@page import="com.ben.guaji.vo.GuaJiConstant"%>
请选择挂机类型:<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n4" />
<postfield name="type" value="<%=GuaJiConstant.DOUBLE_G %>" />
</go>
双倍经验,幸运,掉率离线挂机（<%=GuaJiConstant.DOUBLE_G_YUANBAO %><%=GameConfig.getYuanbaoName() %>/分钟） 
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n4" />
<postfield name="type" value="<%=GuaJiConstant.FIVE_G %>" />
</go>
五倍经验,幸运,掉率离线挂机（<%=GuaJiConstant.FIVE_G_YUANBAO %><%=GameConfig.getYuanbaoName() %>/分钟） 
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n4" />
<postfield name="type" value="<%=GuaJiConstant.EIGHT_G %>" />
</go>
八倍经验,幸运,掉率离线挂机（<%=GuaJiConstant.EIGHT_G_YUANBAO %><%=GameConfig.getYuanbaoName() %>/分钟） 
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n4" />
<postfield name="type" value="<%=GuaJiConstant.TEN_G %>" />
</go>
十倍经验,幸运,掉率离线挂机（<%=GuaJiConstant.TEN_G_YUANBAO %><%=GameConfig.getYuanbaoName() %>/分钟） 
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n1" />
</go>
返回
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
