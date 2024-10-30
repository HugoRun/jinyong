<%@ include file="/init/templete/game_head.jsp" %>
<%@ page pageEncoding="UTF-8"%>
${role_name }：微臣拜见陛下！帝俊：爱卿，你终于来了。${role_name }：陛下，眼下战况如何？帝俊：哎……${role_name }：陛下，我天庭有周天星辰大阵，难道这次还不能让那群不实天数的巫族彻底臣服于我天庭的威严之下吗？<br/>
<br/>
<anchor>确定<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/guide.do?step=end")%>" method="get"/></anchor>
</p>
</card>
</wml>