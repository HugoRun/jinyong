<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>

<c:if test="${roleInfo.settingInfo.indexChat==1}">
<%@ include file="/init/caidan_index.jsp"%>
<c:if test="${!empty chatHint }">${chatHint }<br/></c:if>
<input name="chatContent" size="10" type="text" maxlength="20" />
<c:choose>
<c:when test="${chatChannel==1}">(公)</c:when>
<c:when test="${chatChannel==2}">(种)</c:when>
<c:when test="${chatChannel==3}">(队)</c:when>
<c:when test="${chatChannel==4}">(氏)</c:when>
<c:otherwise>
<c:set var="chatChannel" value="1"/>(公)
</c:otherwise>
</c:choose>
<anchor>发言
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/communioninfoaction.do?cmd=n9")%>">
<postfield name="chatChannel" value="${chatChannel }" />
<postfield name="chatContent" value="$(chatContent)" />
</go>
</anchor><br/>

</c:if>