<%@ page pageEncoding="UTF-8"%>
<%@ include file="../inc/faction_head.jsp"%>
${faction.fullName }<br/>
----------------------<br/>
<c:if test="${faction.lastedNotice.content!=null }">
${faction.lastedNotice.content}<br/>
</c:if>
<anchor>更多公告
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=noticeList")%>">
<postfield name="page_no" value="1"/>
</go>
</anchor><br/>
----------------------<br/>
<anchor>氏族信息<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=des")%>"></go></anchor><br/>
<anchor>氏族管理<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=manageIndex")%>"></go></anchor><br/>
<anchor>祠堂管理<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fBuild.do?cmd=buildIndex")%>"></go></anchor><br/>
<anchor>退出氏族<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=exitHint")%>"></go></anchor><br/>
<%@ include file="/init/templete/game_foot.jsp"%>