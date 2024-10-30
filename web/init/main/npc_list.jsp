<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<c:if test="${!empty npcs}">
兽:
<c:forEach items="${npcs}" var="npc" varStatus="status">
<anchor>${npc.npcName }
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/attackNPC.do?cmd=n4")%>">
<postfield name="nPk" value="${status.index }" />
</go>
</anchor>
</c:forEach>
<br/>
</c:if>