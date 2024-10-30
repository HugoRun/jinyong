<%@ page pageEncoding="UTF-8"%>
<%@ include file="../inc/faction_head.jsp"%>
请输入创建的氏族名称（5个字符之内）:<br/>
<input type="text" name="fName" size="10" maxlength="10"/><br/>
<anchor>确定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=create")%>" method="post">
<postfield name="fName" value="$(fName)"/>
</go>
</anchor><br/>
<%@ include file="../inc/return_faction.jsp"%>