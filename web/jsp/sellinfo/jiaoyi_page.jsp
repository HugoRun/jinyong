<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<anchor>赠送灵石
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/jiaoyi.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="pByPk" value="${pByPk }" />
</go>
</anchor>
<br/>
<anchor>赠送物品
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/jiaoyi.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="pByPk" value="${pByPk }" />
</go>
</anchor>
<br/>
<anchor>交易物品
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/xiaoshou.do")%>"> 
<postfield name="pByPk" value="${pByPk }" />
</go>
</anchor>
<br/>
<!-- 
<anchor>交易宠物
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/swapaction.do")%>">
<postfield name="cmd" value="n9" />
<postfield name="pByPk" value="${pByPk }" />
</go>
</anchor>
<br/> -->
<anchor>返回
<go method="post"  href="<%=response.encodeURL(GameConfig.getContextPath() + "/swapaction.do")%>">
<postfield name="cmd" value="n13" />   
<postfield name="pPks" value="${pByPk }" /> 
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
