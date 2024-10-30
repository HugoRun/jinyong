<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<anchor>快捷键设置
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
<postfield name="aPpk" value="${playerA.PPk }" />
<postfield name="bPpk" value="${playerB.PPk }" />
<postfield name="cmd" value="n1" />
<postfield name="daguai" value="2" />
<postfield name="pk" value="pk" />
</go>
</anchor>
<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>