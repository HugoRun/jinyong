<%@ page pageEncoding="UTF-8" isErrorPage="false"%>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="${field }" /></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>

