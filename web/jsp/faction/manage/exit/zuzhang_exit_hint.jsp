<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
对不起,您为族长,无法退出氏族,请先将族长职位转让!<br/>
<anchor>转让族长<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=zlList")%>" method="get"/></anchor><br/>
<%@ include file="../../inc/return_faction.jsp"%>