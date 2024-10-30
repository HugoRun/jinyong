<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n1")%>"></go>状态</anchor> 
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n3")%>"></go>装备</anchor> 
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n5")%>"></go>技能</anchor>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/credit.do?cmd=n1")%>"></go>声望</anchor>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/honour.do?cmd=n1")%>"></go>称号</anchor><br/> 