<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName}<br/>
请输入所要邀请的玩家姓名:<br/>
<input type="text" name="roleName" size="10" maxlength="10"/>
<anchor>确定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=invite")%>" method="post">
<postfield name="roleName" value="$(roleName)"/>
</go>
</anchor><br/>
<%@ include file="../../inc/return_faction_manage.jsp"%>