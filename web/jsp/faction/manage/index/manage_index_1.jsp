<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName }<br/>
<anchor>查看申请
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=applyList")%>">
<postfield name="page_no" value="1"/>
</go>
</anchor>
|<anchor>邀请加入<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=inviteHint")%>"></go></anchor><br/>
<anchor>发布招募<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=inputRecruit")%>"></go></anchor><br/>
尊敬的护法大人，你具有以下权力:<br/>
1.添加成员，即同意玩家加入氏族的申请，以及邀请玩家加入氏族<br/>
2.发布招募，通过系统发布招募族众信息<br/>
3.发布公告，发布或删除氏族公告(请于公告页面进行管理)<br/>
<%@ include file="../../inc/return_faction.jsp"%>