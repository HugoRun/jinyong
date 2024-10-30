<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName }<br/>
<anchor>查看申请
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=applyList")%>">
<postfield name="page_no" value="1"/>
</go>
</anchor>
|<anchor>邀请加入<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=inviteHint")%>"></go></anchor><br/>
<anchor>职位管理<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=jobMList")%>">
<postfield name="page_no" value="1"/>
</go></anchor>
|<anchor>称号管理<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=titleMList")%>">
<postfield name="page_no" value="1"/>
</go>
</anchor><br/>
<anchor>发布招募<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=inputRecruit")%>"></go></anchor>
<c:if test="${faction.isDisband==true }">
|<anchor>接管氏族<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=assume")%>"></go></anchor>
</c:if>
<br/>
解散氏族<br/>
尊敬的长老大人，你具有以下权力:<br/>
1.添加成员，即同意玩家加入氏族的申请，以及邀请玩家加入氏族<br/>
2.逐出成员，将氏族各成员逐出氏族<br/>
3.职位管理，晋升或降低所有族众的职位<br/>
4.称号管理，修改各族众的氏族称号<br/>
5.发布招募，通过系统发布招募族众信息<br/>
6.公告管理，发布或删除氏族公告(请于公告页面进行管理)<br/>
7.升级祠堂，对祠堂进行升级（请于祠堂管理页面进行管理）<br/>
8.接管氏族，可接管即将解散的氏族，挽回氏族<br/>
<%@ include file="../../inc/return_faction.jsp"%>