<%@page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
您确定要给予${name}${pSilver }<%=GameConfig.getMoneyUnitName() %>吗?<br/>
<anchor> 确定
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
<postfield name="cmd" value="n2" /> 
<postfield name="pByPk" value="${pByPk }" />
<postfield name="pSilver" value="${pSilver }" />
</go>
</anchor> 
<anchor>取消
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>"> 
<postfield name="cmd" value="n2" /> 
<postfield name="pByPk" value="${pByPk }" />  
</go>
</anchor>  
<%@ include file="/init/templete/game_foot.jsp"%>