<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
${role_title.name }(${role_title.typeName })<br/>
${role_title.des }<br/>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath() + "/title.do?cmd=n1")%>" ></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>