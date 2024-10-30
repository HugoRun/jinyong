<%@page pageEncoding="UTF-8" isELIgnored ="false"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<c:forEach items="${me.roleShortCutInfo.shortList }" var="shortcut" begin="0" end="5"  varStatus="status">
<c:choose>
<c:when test="${shortcut.scType!=6 && shortcut.scType!=7 }">
<anchor>${shortcut.simpleDisplay }
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/pk.do") %>">
<postfield name="cmd" value="n6" />
<postfield name="bPpk" value="${playerB.PPk}" />
<postfield name="sc_pk" value="${shortcut.scPk}" />
<postfield name="unAttack" value="unAttack" />
</go>
</anchor>
</c:when>
<c:otherwise>
${shortcut.simpleDisplay }
</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${(status.index+1)%3!=0 }">
|
</c:when>
<c:otherwise>
<br/>
</c:otherwise>
</c:choose>
</c:forEach>
