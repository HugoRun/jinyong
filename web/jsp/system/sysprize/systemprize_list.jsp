<%@page pageEncoding="UTF-8" %>
<%@ include file="/init/templete/game_head.jsp"%>
${vo.prizedisplay }<br/>
您可以获得${display }!<br/>
<anchor>领取
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sysprize.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="id" value="${id }" />
</go>
</anchor>
<%@ include file="inc/return_prize.jsp"%>
