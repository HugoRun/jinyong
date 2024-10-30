<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<anchor>公<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/walk.do?chatChannel=1")%>" ></go></anchor>|<anchor>种<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/walk.do?chatChannel=2")%>" ></go></anchor>|<anchor>氏<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/walk.do?chatChannel=4")%>" ></go></anchor>|<anchor>队<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/walk.do?chatChannel=3")%>" ></go></anchor><br/>
