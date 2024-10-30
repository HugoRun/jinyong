<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.display }
--------------------<br/>
将祠堂升至${material.grade }级，${material.MDes }，您确定要将祠堂升至${material.grade }级吗？<br/>
<anchor>升级<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=upgradeCT")%>" method="get"/></anchor><br/>
<%@ include file="../../inc/return_build_manage.jsp"%>