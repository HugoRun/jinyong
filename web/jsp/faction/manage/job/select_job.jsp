<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
<%@page import="com.ls.model.organize.faction.Faction" %>
${faction.fullName }<br/>
请选择您所要变更的职位:<br/>
<c:if test="${basicInfo.FJob==3 }">
<anchor>长老
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=selectJob")%>">
<postfield name="mId" value="${mId}"/>
<postfield name="job" value="<%=Faction.ZHANGLAO%>"/>
</go>
</anchor>
|
</c:if>
<anchor>护法
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=selectJob")%>">
<postfield name="mId" value="${mId}"/>
<postfield name="job" value="<%=Faction.HUFA%>"/>
</go>
</anchor>
|
<anchor>族众
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=selectJob")%>">
<postfield name="mId" value="${mId}"/>
<postfield name="job" value="<%=Faction.ZUZHONG%>"/>
</go>
</anchor>
<br/>
<anchor>返回<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=jobMList")%>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>