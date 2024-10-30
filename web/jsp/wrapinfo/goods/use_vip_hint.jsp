<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
${hint }
<anchor>确定
<go href="<%= response.encodeURL(GameConfig.getContextPath()+"/vip.do?cmd=n1")%>" method="post">
<postfield name="prop_id" value="${goods_id }"/>
</go>
</anchor><br/>
<%@ include file="../include/return_wrap.jsp"%>
