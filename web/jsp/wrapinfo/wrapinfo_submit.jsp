<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
${display }<br/>
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do") %>">
<postfield name="cmd" value="n20"/>
</go>
</anchor><br/>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1")%>" ></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
