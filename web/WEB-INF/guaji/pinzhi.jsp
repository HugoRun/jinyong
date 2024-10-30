<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.guaji.vo.GuaJiConstant"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.map.SceneVO"%>
请设置拾取装备的品质：<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n8" />
<postfield name="type" value="<%=GuaJiConstant.ALL %>" />
</go>
全部拾取
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n8" />
<postfield name="type" value="<%=GuaJiConstant.YOU %>" />
</go>
仅拾取“优”以上
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n8" />
<postfield name="type" value="<%=GuaJiConstant.JING %>" />
</go>
仅拾取“精”以上
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n8" />
<postfield name="type" value="<%=GuaJiConstant.JI %>" />
</go>
仅拾取“极”以上
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
