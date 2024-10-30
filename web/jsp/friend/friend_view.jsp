<%@page pageEncoding="UTF-8"%>
<%@include file="/init/templete/game_head_with_hint.jsp" %>
${other.display }<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="cmd" value="n4" />
	<postfield name="pByName" value="${pByName }" />
	<postfield name="pByPk" value="${pByPk }" />
	</go>
	悄悄话
	</anchor>
	<br/>
	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sendMail.do?cmd=n4") %>">
	<postfield name="sendPk" value="${pByPk }" />
	<postfield name="mail_type" value="1" />
	<postfield name="from_type" value="1" />
	</go>
	发送邮件
	</anchor>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="cmd" value="n8" />
	<postfield name="pByName" value="${pByName }" />
	<postfield name="pByPk" value="${pByPk }" />
	</go>
	邀请组队
	</anchor>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="cmd" value="n6" />
	<postfield name="pByName" value="${pByName }" />
	<postfield name="pByPk" value="${pByPk }" /> 
	</go>
	删除好友
	</anchor>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/blacklistaction.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="pByName" value="${pByName }" />
	<postfield name="pByPk" value="${pByPk }" />
	</go>
	加入黑名单
	</anchor>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="cmd" value="n2" />
	</go>
	返回
	</anchor>
<%@include file="/init/templete/game_foot.jsp" %>
