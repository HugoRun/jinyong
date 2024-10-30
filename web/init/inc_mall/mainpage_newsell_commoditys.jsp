<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<%@page import="com.ls.pub.config.GameConfig"%>
<c:if test="${!empty newsell_commoditys}">
本周新品:<br/>
<c:forEach items="${newsell_commoditys}" var="commodity">
[
<anchor>购
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/mall.do?cmd=n6")%>">
<postfield name="c_id" value="${commodity.id}"/>
</go>
</anchor>
]
<anchor>${commodity.propName }
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/mall.do?cmd=n6")%>">
<postfield name="c_id" value="${commodity.id }"/>
</go>
</anchor>
(${commodity.prop.propDisplay})
<br/>
</c:forEach>
--------------------<br/>
</c:if>