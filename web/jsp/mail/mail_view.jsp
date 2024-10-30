<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head_with_hint.jsp"%>
题目:${mailInfo.title }<br/>
玩家:${mailInfo.sendName }<br/>
时间:${mailInfo.formatCreateTime}<br/>
内容:${mailInfo.content}<br/>
<%@ include file="inc/mail_attachment.jsp"%>
<anchor>回复
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/sendMail.do?cmd=n4")%>">
<postfield name="sendPk" value="${ mailInfo.sendPk}" />
<postfield name="mail_type" value="${ mailInfo.mailType}" />
<postfield name="from_type" value="2" />
</go>
</anchor>
|
<anchor>删除
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mail.do?cmd=n3")%>">
<postfield name="mailId" value="${ mailInfo.mailId}" />
</go>
</anchor><br/>
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mail.do?cmd=n1")%>">
<postfield name="page_no" value="${page_no }" />
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
