<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
您确定要丢弃${wName }吗?<br/>
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n13" />
<postfield name="pwPk" value="${pwPk }" />
</go>
</anchor><br/>
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n1" />
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
