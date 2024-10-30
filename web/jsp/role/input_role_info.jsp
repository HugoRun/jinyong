<%@ include file="/init/templete/game_head_with_hint.jsp" %>
<%@ page pageEncoding="UTF-8"%>
恭喜你将成为一名骄傲的${raceDes}族战士<br/>
请输入角色名称(名称不能大于5位): <br/>
<input name="name" type="text" maxlength="10" size="10"/><br/>
性别选择:<br/>
<select name="sex" value="1">
<option value="1">男</option>
<option value="2">女</option>
</select><br/>
<anchor>确定创建
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do?cmd=n3")%>" method="post">
<postfield name="race" value="${race}" />
<postfield name="name" value="$(name)" />
<postfield name="sex" value="$(sex)" />
</go>
</anchor><br/>
<br/>
<anchor>重新选取种族<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do?cmd=n1")%>" method="get"/></anchor>
</p>
</card>
</wml>