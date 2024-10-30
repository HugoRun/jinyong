<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
${hint }
请输入您要放入的数量:<br/>
<input type="text" name="propNum" format="*N" maxlength="2" size="2"/>
<anchor>确定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/prop.do?cmd=useStone")%>" method="post">
<postfield name="propId" value="${propId }"/>
<postfield name="propNum" value="$(propNum)"/>
</go>
</anchor><br/>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/prop.do?cmd=stoneList")%>" method="get" /></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>