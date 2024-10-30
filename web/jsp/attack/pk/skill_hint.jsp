<%@ include file="/init/templete/game_head_with_hint.jsp"%>
<%@page pageEncoding="UTF-8"%>
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/pk.do")%>">
<postfield name="cmd" value="n7" />
</go>
</anchor>
<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
