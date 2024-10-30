<%@ include file="/init/templete/game_head.jsp" %>
<%@page pageEncoding="UTF-8"%>
您确定要删除${pGrade }级的${pName }吗?(不可恢复)<br/>
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do") %>">
<postfield name="cmd" value="n9" />
<postfield name="pPk" value="${pPk }" /> 
</go>	
</anchor><br/>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3") %>"/></anchor>
</p>
</card>
</wml>
