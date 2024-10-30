<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
【宝石合成】<br/>
${hint }<br/>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/prop.do?cmd=upgradeIndex")%>" method="get" /></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
