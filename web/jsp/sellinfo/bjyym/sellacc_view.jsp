<%@page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
${ equip_display}
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")%>">
<postfield name="cmd" value="n4" />
<postfield name="sPk" value="${sPk }" />
</go>
</anchor>
</p>
</card>
</wml>
